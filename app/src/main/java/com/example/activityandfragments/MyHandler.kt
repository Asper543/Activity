package com.example.activityandfragments

import android.os.Handler
import android.os.Message


const val ITEM_ADDED = 1
class MyHandler(private val callback: Callback) : Handler() {
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
        callback.handleMessage(msg)
    }

    fun closedJob(): MyHandler {
        jobThread?.interrupt()
        jobThread = null
        return this
    }

    fun startJob() = jobThread?.start()

}