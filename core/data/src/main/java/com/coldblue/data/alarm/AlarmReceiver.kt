package com.coldblue.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {

    private lateinit var manager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    companion object {
        const val MY_ID = "MYID"
        const val MY_NAME = "MYNAME"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("TAG", "onReceive: 받았음")
        manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(
            NotificationChannel(
                MY_ID,
                MY_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        )

        builder = NotificationCompat.Builder(context, MY_ID)

//        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//
//        context.startActivity(activityIntent)
    }
}