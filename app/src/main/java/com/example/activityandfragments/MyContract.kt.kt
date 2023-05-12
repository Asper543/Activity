package com.example.activityandfragments

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
const val  BUTT_COUNT="BUTT_COUNT"
class MyContract<T : AppCompatActivity>(private val activityClass: Class<T>,
                                        private val data:ArrayList<NameColor>) :
    ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        Log.i(TAG,data.toString())
        return Intent(context, activityClass).putParcelableArrayListExtra(BUTT_COUNT,data).apply {
            Log.i(TAG,"IntentExtras isNull=${this.extras==null}")
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return if ((resultCode == AppCompatActivity.RESULT_OK) and (intent!!.hasExtra(TAG))) {
            intent.getStringExtra(TAG)
        } else null
    }
}