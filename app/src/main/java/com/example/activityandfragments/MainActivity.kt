package com.example.activityandfragments

import android.annotation.SuppressLint
import android.os.*
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.activityandfragments.my_activity_result_launchers.MyLauncher
import com.example.activityandfragments.jobs.MyContactLoader
import com.example.activityandfragments.user_notification.MyNotification
import com.example.activityandfragments.user_notification.MyToast
import com.example.activityandfrugments.R


val NAME_COLOR = arrayOf(
    NameColor(name = "yellow", color = R.color.yellow),
    NameColor(name = "Red", color = R.color.red),
    NameColor(name = "Green", color = R.color.green),
    NameColor(name = "purple_200", color = R.color.purple_200),
    NameColor(name = "purple_700", color = R.color.purple_700)
)


class MainActivity : AppCompatActivity() {
    private lateinit var buttonColor: Button
    private lateinit var buttonContacts: Button
    private lateinit var recyclerView: RecyclerView
    private val contactAdapter = ContactAdapter()
    private val toast = MyToast(this)
    private val launcher = MyLauncher(this)

    @SuppressLint("NotifyDataSetChanged")
    private val myHandler = MyHandler {
        if (it.what == ITEM_ADDED) contactAdapter.notifyDataSetChanged()
        true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private val changeColor = launcher.changeColor{
        if (it != null) {
            buttonColor.setBackgroundColor(getColor(it.color))
            MyNotification(this).createNotification(it)
            toast.showColorToast(it)
        }
    }

    @SuppressLint("Range")
    private val contacts = launcher.contacts{
        if (!it) {
            toast.showContactsToast()
        } else {
            val job = MyContactLoader.grtContactJob(myHandler, contentResolver, contactAdapter)
            myHandler.closedJob().addJob(job).startJob()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            this.adapter = contactAdapter
        }

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
}

