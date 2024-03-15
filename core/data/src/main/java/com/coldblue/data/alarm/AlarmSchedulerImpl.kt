package com.coldblue.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.coldblue.data.util.toMillis
import com.coldblue.model.AlarmItem
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
) : AlarmScheduler {
    override fun schedule(item: AlarmItem) {
        if (item.time == null) return

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
        } catch (e: Exception) {
            Log.e("TAG", "schedule: ${e.message}")
        }
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            item.time.toMillis(),
//            PendingIntent.getBroadcast(
//                context,
//                item.id,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

//    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun reset() {
//        alarmManager.cancelAll()
    }

}