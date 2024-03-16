package com.coldblue.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.coldblue.data.util.toMillis
import com.coldblue.database.dao.AlarmDao
import com.coldblue.model.AlarmItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val alarmDao: AlarmDao
) : AlarmScheduler {
    override fun add(item: AlarmItem) {
        if (item.time == null) return

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TODO_TITLE, item.title)
        }
        try {
            alarmManager.setExactAndAllowWhileIdle(
                // TODO  ELAPSED_REALTIME_WAKEUP -> 나라별 시간 기준으로 알람을 울리는거라는데
                AlarmManager.RTC_WAKEUP,
                item.time!!.toMillis(),
                PendingIntent.getBroadcast(
                    context,
                    item.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            CoroutineScope(Dispatchers.IO).launch {
                alarmDao.addAlarmId(item.id)
            }
        } catch (e: Exception) {
            Log.e("TAG", "schedule: ${e.message}")
        }
    }

    override fun cancel(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        CoroutineScope(Dispatchers.IO).launch {
            alarmDao.deleteAlarmId(id)
        }
    }

    override fun reset() {
        CoroutineScope(Dispatchers.IO).launch {
            alarmDao.getAllAlarmId().forEach { cancel(it) }
        }
    }

}