package com.coldblue.haru_mandalart.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.coldblue.data.receiver.NOTICE_TITLE
import com.coldblue.data.receiver.notification.NotificationAppService
import com.coldblue.haru_mandalart.AlarmActivity
import com.coldblue.haru_mandalart.MainActivity
import com.coldblue.haru_mandalart.util.HMApplication
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NotificationAppServiceImpl @Inject constructor(
    private val context: Context
): NotificationAppService {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showAlarm(text: String) {
        Logger.d("Alarm ON")
        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.apply { putExtra(NOTICE_TITLE, text) }
        context.startActivity(activityIntent)
    }

    override fun showNotification(text: String) {
        Logger.d("Notice ON $text")
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(com.coldblue.data.R.drawable.notification_icon)
            .setContentTitle("Todo 알림")
            .setContentText(text)
            .setContentIntent(activityPendingIntent)
            .setVibrate(LongArray(1)) // 사이즈는 영향 X
            .setAutoCancel(true) // 알람 패널 클릭 시 사라짐
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    override fun isAppForeground(): Boolean {
        val app = context.applicationContext as HMApplication
        return app.lifecycleObserver.isAppRunning
    }

    companion object {
        const val CHANNEL_ID = "todo_channel"
        const val CHANNEL_NAME = "Todo"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val description = "Todo알림을 받습니다."
    }
}