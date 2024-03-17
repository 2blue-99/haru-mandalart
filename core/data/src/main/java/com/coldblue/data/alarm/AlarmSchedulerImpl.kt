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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val alarmDao: AlarmDao
) : AlarmScheduler {

    override fun add(item: AlarmItem) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TODO_TITLE, item.title)
        }
        try {
            alarmManager.setExactAndAllowWhileIdle(
                // TODO  ELAPSED_REALTIME_WAKEUP -> 나라별 시간 기준으로 알람을 울리는거라는데 바꿀까
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
                alarmDao.addAlarm(item.asEntity())
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
            alarmDao.deleteAlarm(id)
        }
    }

    override fun cancelAll() {
        CoroutineScope(Dispatchers.IO).launch {
            alarmDao.getAllAlarm().forEach {
                Logger.d(it)
                cancel(it.id)
            }
        }
    }

}