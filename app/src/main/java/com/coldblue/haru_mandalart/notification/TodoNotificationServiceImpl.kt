package com.coldblue.haru_mandalart.notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.coldblue.data.alarm.TodoNotificationService
import com.coldblue.designsystem.theme.HMColor
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
            .setContentTitle("Todo 알림")
//            .setColor(0xFF432ED1.toInt()) // HM Primary 색
//            .setColor(ContextCompat.getColor(context, R.color.purple_500)) // 아이콘 배경 색
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
        const val CHANNEL_NAME = "오늘 할 일"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val description = "오늘 일정에서 계획된 시간이 지났음에도 완료가 안된 할 일에 대한 알림을 받습니다."

    }
}