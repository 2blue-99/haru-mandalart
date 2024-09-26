package com.coldblue.data.receiver.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.coldblue.data.mapper.AlarmMapper.asEntity
import com.coldblue.data.receiver.NOTICE_ID
import com.coldblue.data.receiver.NOTICE_TITLE
import com.coldblue.data.receiver.AlarmReceiver
import com.coldblue.data.util.toMillis
import com.coldblue.database.dao.NotificationDao
import com.coldblue.model.NotificationAlarmItem
import javax.inject.Inject

class NotificationSchedulerImpl @Inject constructor(
    private val context: Context,
    private val notificationDao: NotificationDao
): NotificationScheduler {
    override suspend fun add(item: NotificationAlarmItem) {
        val alarmManger = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(NOTICE_TITLE, item.title)
            putExtra(NOTICE_ID, item.id)
        }
        val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context, item.id, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, item.id, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        Log.e("TAG", "alarm 시간 set : ${item.time}")
        alarmManger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, item.time!!.toMillis(), pendingIntent)
        notificationDao.addNotification(item.asEntity())
    }

    override suspend fun cancel(alarmCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        alarmManager.cancel(pendingIntent)
        notificationDao.deleteNotification(alarmCode)
    }

    override suspend fun cancelAll() {
        notificationDao.getAllNotification().forEach {
            cancel(it.id)
        }
    }
}

