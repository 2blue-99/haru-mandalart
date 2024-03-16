package com.coldblue.haru_mandalart.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.coldblue.data.R
import com.coldblue.data.alarm.TodoNotificationService
import com.coldblue.haru_mandalart.MainActivity
import javax.inject.Inject

class TodoNotificationServiceImpl @Inject constructor(
    private val context: Context,
) : TodoNotificationService {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(message: String) {

        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPeningIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp).setContentTitle("오늘 할 일")
            .setContentText(message)
            .setContentIntent(activityPeningIntent)
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    companion object {
        const val CHANNEL_ID = "todo_channel"
        const val CHANNEL_NAME = "오늘 할 일"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val description = "오늘 일정에서 계획된 시간이 지났음에도 완료가 안된 할 일에 대한 알림을 받습니다."

    }
}