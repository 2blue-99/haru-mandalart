package com.coldblue.haru_mandalart.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TodoNotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val service = TodoNotificationServiceImpl(context)
        service.showNotification("눌림요")
    }
}