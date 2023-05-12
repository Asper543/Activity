package com.example.activityandfragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.activityandfrugments.R
import java.util.ArrayList



class SecondActivity : AppCompatActivity() {
    private var buttonsList = ArrayList<NameColor>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
         intent?.extras?.getParcelableArrayList(BUTT_COUNT,NameColor::class.java)?.forEach {
             buttonsList.add(it)
         }

        val scrollView = findViewById<ScrollView>(R.id.scrollView1)
        val buttonList = arrayListOf<Button>()
        try {
            buttonsList.forEach {
                val button = Button(this).apply {
                    with(this) {
                        text = it.name
                        setBackgroundResource(it.color)
                        setOnClickListener {
                            val intent = Intent().putExtra(TAG, this.text)
                            this@SecondActivity.setResult(RESULT_OK, intent)
                        }
                        buttonList.add(this)
                    }
                }
                scrollView.addView(button)
            }
        } catch (e: java.lang.Exception) {
            print(e.javaClass)
        }
    }

}