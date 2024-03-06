package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.notification.TodoNotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var todoNotificationService: TodoNotificationService

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(TODO_TITLE) ?: return
        Log.e("TAG", "onReceive: $message")
        showNotification(2, message)

    }

    fun showNotification(cnt: Int, message: String) {
        todoNotificationService.showNotification(cnt, message)
    }

}

internal const val TODO_TITLE = "TODO_TITLE"