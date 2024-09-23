package com.coldblue.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.coldblue.data.util.toMillis
import com.coldblue.model.NotificationAlarmItem
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context
): AlarmScheduler {
    override fun addAlarm(alarmItem: NotificationAlarmItem) {
        val alarmManger = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(context, AlarmReceiver::class.java)
        receiverIntent.apply {
            putExtra(ALARM_TITLE, alarmItem.title)
            putExtra(ALARM_ID, alarmItem.id)
        }

        val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context, alarmItem.id, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, alarmItem.id, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        Log.e("TAG", "alarm 시간 set : ${alarmItem.time}")
        alarmManger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmItem.time!!.toMillis(), pendingIntent)
    }

    override fun cancelAlarm(alarmCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, alarmCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        alarmManager.cancel(pendingIntent)
    }
}

