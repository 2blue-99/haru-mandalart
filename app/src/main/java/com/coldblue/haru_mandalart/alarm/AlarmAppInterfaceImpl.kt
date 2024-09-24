package com.coldblue.haru_mandalart.alarm

import android.content.Context
import android.content.Intent
import com.coldblue.data.receiver.alarm.AlarmAppInterface
import com.coldblue.haru_mandalart.AlarmActivity
import javax.inject.Inject

class AlarmAppInterfaceImpl @Inject constructor(
    private val context: Context
): AlarmAppInterface {
    override fun showAlarm(text: String) {
        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(activityIntent)
    }
}