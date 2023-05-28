package com.example.activityandfragments.my_activity_result_launchers.myContracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.activityandfragments.NameColor
import com.example.activityandfragments.SecondActivity

const val TAG = "First"
const val BUTT_COUNT = "BUTT_COUNT"
class MyContract:
    ActivityResultContract<Array<NameColor>, NameColor?>() {
    override fun createIntent(context: Context, input:Array<NameColor>): Intent=
        Intent(context, SecondActivity::class.java).putExtra(BUTT_COUNT,input)

    override fun parseResult(resultCode: Int, intent: Intent?): NameColor?=
        intent?.getParcelableExtra(TAG) as NameColor?
}


