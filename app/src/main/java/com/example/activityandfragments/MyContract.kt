package com.example.activityandfragments

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts


const val  BUTT_COUNT="BUTT_COUNT"

class MyContract:
    ActivityResultContract<Array<NameColor>, NameColor?>() {
    override fun createIntent(context: Context, input:Array<NameColor>): Intent=
        Intent(context, SecondActivity::class.java).putExtra(BUTT_COUNT,input)

    override fun parseResult(resultCode: Int, intent: Intent?): NameColor?=
        intent?.getParcelableExtra(TAG) as NameColor?
}


