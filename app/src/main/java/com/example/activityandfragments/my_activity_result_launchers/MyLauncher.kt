package com.example.activityandfragments.my_activity_result_launchers

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.activityandfragments.my_activity_result_launchers.myContracts.MyContract
import com.example.activityandfragments.NameColor

class MyLauncher(private val activity: AppCompatActivity) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeColor(callback: ActivityResultCallback<NameColor?>) =
        activity.registerForActivityResult(
            MyContract(), callback
        )


    @SuppressLint("Range")
    fun contacts(callback: ActivityResultCallback<Boolean>) = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(), callback
    )
}