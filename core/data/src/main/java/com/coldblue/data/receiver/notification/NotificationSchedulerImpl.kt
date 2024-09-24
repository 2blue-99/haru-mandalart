package com.coldblue.data.receiver.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.mapper.AlarmMapper.asEntity
import com.coldblue.data.receiver.AlarmReceiver
import com.coldblue.data.receiver.TODO_ID
import com.coldblue.data.receiver.TODO_TITLE
import com.coldblue.data.util.toMillis
import com.coldblue.database.dao.NotificationDao
import com.coldblue.model.NotificationAlarmItem
import javax.inject.Inject

class NotificationSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val notificationDao: NotificationDao
) : NotificationScheduler {

    override suspend fun add(item: NotificationAlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TODO_TITLE, item.title)
            putExtra(TODO_ID, item.id)
        }
        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                item.time!!.toMillis(),
                PendingIntent.getBroadcast(
                    context,
                    item.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            notificationDao.addNotification(item.asEntity())

        } catch (e: Exception) {
            Log.e("TAG", "schedule: ${e.message}")
        }
    }

    override suspend fun cancel(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        notificationDao.deleteNotification(id)
    }

    override suspend fun cancelAll() {
        notificationDao.getAllNotification().forEach {
            cancel(it.id)
        }
    }

}