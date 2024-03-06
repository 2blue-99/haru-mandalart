package com.coldblue.data.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.coldblue.data.R

class TodoNotificationService(
    private val context: Context
) {
//    val activityIntent = Intent(context,)
    fun showNotification(text: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp).setContentTitle("타이틀이여")
            .setContentText(text)

    }

    companion object {
        const val CHANNEL_ID = "todo_channel"
        const val CHANNEL_NAME = "오늘 할 일"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val description = "오늘 일정에서 계획된 시간이 지났음에도 완료가 안된 할 일에 대한 알림을 받습니다."

    }
}