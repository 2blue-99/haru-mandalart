package com.coldblue.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.mapper.AlarmMapper.asEntity
import com.coldblue.data.util.toMillis
import com.coldblue.database.dao.AlarmDao
import com.coldblue.model.AlarmItem
import com.orhanobut.logger.Logger
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val alarmDao: AlarmDao
) : AlarmScheduler {

    override suspend fun add(item: AlarmItem) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TODO_TITLE, item.title)
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
            alarmDao.addAlarm(item.asEntity())

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
        alarmDao.deleteAlarm(id)
    }

    override suspend fun cancelAll() {
        alarmDao.getAllAlarm().forEach {
            Logger.d(it)
            cancel(it.id)
        }
    }

}