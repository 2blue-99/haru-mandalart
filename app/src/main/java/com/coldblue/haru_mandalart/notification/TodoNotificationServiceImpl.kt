package com.coldblue.haru_mandalart.notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.coldblue.data.alarm.TodoNotificationService
import com.coldblue.haru_mandalart.MainActivity
import com.coldblue.haru_mandalart.R
import javax.inject.Inject

class TodoNotificationServiceImpl @Inject constructor(
    private val context: Context,
) : TodoNotificationService {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @SuppressLint("ResourceType")
    override fun showNotification(text: String) {

        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.coldblue.data.R.drawable.notification_icon)
            .setContentTitle(R.string.notice_title.toString())
            .setContentText(text)
            .setContentIntent(activityPendingIntent)
            .setVibrate(LongArray(1)) // 사이즈는 영향 X
            .setAutoCancel(true) // 알람 패널 클릭 시 사라짐
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    companion object {
        const val CHANNEL_ID = "todo_channel"
        const val CHANNEL_NAME = "Todo"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val description = "Todo알림을 받습니다."
    }
}