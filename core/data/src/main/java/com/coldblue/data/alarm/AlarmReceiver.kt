package com.coldblue.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver(), AlarmReceiver.TestInterface {

    interface TestInterface {
        abstract fun test()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("TAG", "onReceive: 받았음")



        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(activityIntent)
    }
}

internal const val ALARM_TITLE = "ALARM_TITLE"
internal const val ALARM_ID = "ALARM_ID"