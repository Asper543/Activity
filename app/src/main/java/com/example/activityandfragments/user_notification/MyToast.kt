package com.example.activityandfragments.user_notification

import android.content.Context
import android.widget.Toast
import com.example.activityandfragments.NameColor

class MyToast (private val context: Context){

     fun showContactsToast() {
        Toast.makeText(
            context,
            "Please open app permissions and check Contacts allow",
            Toast.LENGTH_SHORT
        ).show()
    }

     fun showColorToast(nameColor: NameColor) {
        Toast.makeText(
            context,
            "Pressed\t${nameColor.name}\tcolor",
            Toast.LENGTH_SHORT
        ).show()
    }







}