package com.example.activityandfragments

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.activityandfragments.my_activity_result_launchers.myContracts.BUTT_COUNT
import com.example.activityandfragments.my_activity_result_launchers.myContracts.TAG
import com.example.activityandfrugments.R
import java.util.ArrayList

class SecondActivity : AppCompatActivity() {
    private var buttonsList = ArrayList<NameColor>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = AnimationUtils.loadAnimation(this, R.anim.combo)
        setContentView(R.layout.activity_2)
        intent?.extras?.getParcelableArray(BUTT_COUNT)?.forEach {
            buttonsList.add(it as NameColor)
        }
        val scrollView = findViewById<ScrollView>(R.id.scrollView1)
        val buttonList = arrayListOf<Button>()

        try {
            buttonsList.forEach {nameColor->
                val button = Button(this).apply {
                    text = nameColor.name
                    setBackgroundResource(nameColor.color)
                    setOnClickListener {
                        setResult(RESULT_OK, Intent().putExtra(TAG,nameColor))
                        this@SecondActivity.finish()
                    }
                    buttonList.add(this)
                }
                button.startAnimation(animation.apply { duration = 3000 })
              (scrollView.getChildAt(0) as ViewGroup).addView(button)

            }
        } catch (e: java.lang.Exception) {
            print(e.javaClass)
        }
    }

}