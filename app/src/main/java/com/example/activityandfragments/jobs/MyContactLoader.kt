package com.example.activityandfragments.jobs

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import com.example.activityandfragments.ITEM_ADDED
import com.example.activityandfragments.Contact
import com.example.activityandfragments.ContactAdapter
import com.example.activityandfragments.my_activity_result_launchers.myContracts.TAG

class MyContactLoader() {

    private var jobThread: Thread? = null

    @SuppressLint("Range")
    private constructor(
        handler: Handler,
        resolver: ContentResolver,
        contactAdapter: ContactAdapter
    ) : this() {
        jobThread = Thread{
            val cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null
            )
            cursor.use { cur->
                if ((cur != null) and (cur?.count!! > 0)) {
                    while ((cur.moveToNext()) and (!Thread.currentThread().isInterrupted)) {
                        val id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                        val name =
                            cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        if (cur.getInt(
                                cur.getColumnIndex(
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER
                                )
                            ) > 0
                        ) {
                            val phoneCursor = resolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                Array(1) { id }, null
                            )
                            phoneCursor.use {
                                if (it != null) {
                                    while (it.moveToNext()) {
                                        val phone = it.getString(
                                            it.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER
                                            )
                                        )
                                        contactAdapter.setData(Contact(name, phone))
                                        Log.i(TAG, "$name\t $phone")
                                        handler.sendEmptyMessage(ITEM_ADDED)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    companion object Builder {

        fun grtContactJob(
            handler: Handler,
            resolver: ContentResolver,
            contactAdapter: ContactAdapter
        ): Thread {
            return MyContactLoader(handler, resolver, contactAdapter).jobThread!!
        }
    }
}