package com.coldblue.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TodoNotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val service = TodoNotificationService(context)
        service.showNotification(++Counter.value,"눌림요")
    }
}