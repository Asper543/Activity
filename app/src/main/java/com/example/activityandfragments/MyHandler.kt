package com.example.activityandfragments

import android.os.Handler
import android.os.Message

class MyHandler( val collback: Callback) : Handler() {
    private var jobThread: Thread? = null

    fun addJob(thread: Thread): MyHandler {
        if ((jobThread != null) and (jobThread?.isInterrupted == false)) {
            jobThread!!.interrupt()
        } else {
            jobThread = thread
        }
        return this
    }

    override fun handleMessage(msg: Message) {
        collback.handleMessage(msg)
    }

    fun closedJob():MyHandler {
        jobThread?.interrupt()
        jobThread = null
        return this
    }

    fun startJob() = jobThread?.start()
}