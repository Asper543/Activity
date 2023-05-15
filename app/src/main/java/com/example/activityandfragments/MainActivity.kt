package com.example.activityandfragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.*
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.activityandfrugments.R

const val TAG = "First"
const val  BUTT_COUNT="BUTT_COUNT"

val NAME_COLOR = arrayOf(
    NameColor(name = "yellow", color = R.color.yellow),
    NameColor(name = "Red", color = R.color.red),
    NameColor(name = "Green", color = R.color.green),
    NameColor(name = "purple_200", color = R.color.purple_200),
    NameColor(name = "purple_700", color = R.color.purple_700)
)

const val NOTIFICATION = 1
const val CHANNEL_ID = "ChangeColorNotification "
const val ITEM_ADDED = 1

@RequiresApi(Build.VERSION_CODES.O)
val NOTIFICATION_CHANNEL =
    NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)

class MainActivity : AppCompatActivity() {
    private lateinit var buttonColor: Button
    private lateinit var buttonContacts: Button
   private lateinit var recyclerView: RecyclerView
    private val contactAdapter = ContactAdapter()

    @SuppressLint("NotifyDataSetChanged")
    private val myHandler = MyHandler {
        if (it.what == ITEM_ADDED) contactAdapter.notifyDataSetChanged()
        true
    }
   private lateinit var manager: NotificationManager


    @RequiresApi(Build.VERSION_CODES.O)
    private val changeColor =
        registerForActivityResult(
            MyContract()
        ) { nameColor ->
            if (nameColor != null) {
                buttonColor.setBackgroundColor(getColor(nameColor.color))
                createNotification(nameColor)
                toastColor(nameColor)
            }
        }

    @SuppressLint("Range")
    private val contacts = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (!it) {
            toastContacts()
        } else {
            myHandler.closedJob().addJob(getContactsThread()).startJob()
        }
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = contactAdapter

        buttonColor = findViewById<Button?>(R.id.but1).apply {
            setOnClickListener {
                myHandler.closedJob()
                buttonContacts.isClickable = true
                changeColor.launch(NAME_COLOR)
            }
        }
        buttonContacts = findViewById<Button>(R.id.but2).apply {
            setOnClickListener {
                contactAdapter.cleared()
                contacts.launch(android.Manifest.permission.READ_CONTACTS)
                it.isClickable = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(nameColor: NameColor) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Color")
            .setColor(getColor(nameColor.color))
            .setContentText("CHANGED ${nameColor.name}")
            .build()
        manager.createNotificationChannel(NOTIFICATION_CHANNEL)
        manager.notify(NOTIFICATION, notification)
    }

    private fun toastContacts() {
        Toast.makeText(
            this,
            "Please open app permissions and check Contacts allow",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun toastColor(nameColor: NameColor) {
        Toast.makeText(
            this,
            "Pressed\t${nameColor.name}\tcolor",
            Toast.LENGTH_SHORT
        ).show()
    }
    @SuppressLint("Range")
    private fun getContactsThread() =
        Thread {
            val resolver = contentResolver
            val cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null
            )
            if ((cursor != null) and (cursor?.count!! > 0)) {
                while ((cursor.moveToNext()) and (!Thread.currentThread().isInterrupted)) {
                    val id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    if (cursor.getInt(
                            cursor.getColumnIndex(
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
                        if (phoneCursor != null) {
                            while (phoneCursor.moveToNext()) {
                                val phone = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                                )
                                contactAdapter.setData(Contact(name, phone))
                                Log.i(TAG, "$name\t $phone")
                                myHandler.sendEmptyMessage(ITEM_ADDED)
                            }
                            phoneCursor.close()
                        }
                    }
                }
                cursor.close()
            }
        }
}




