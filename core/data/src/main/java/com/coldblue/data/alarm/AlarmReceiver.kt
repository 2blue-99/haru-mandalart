package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.notification.TodoNotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
//힐트로 가능
//late init 으로

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var todoNotificationService: TodoNotificationService

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        Log.e("TAG", "onReceive: $message")
        showNotification(2, "이것은 알ㄹ미이다")

    }

    fun showNotification(cnt: Int, text: String) {
        todoNotificationService.showNotification(cnt, text)
    }

}