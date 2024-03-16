package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var todoNotificationService: TodoNotificationService

    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra(TODO_TITLE) ?: return
        showNotification( text)
    }
    private fun showNotification(text: String) {
        todoNotificationService.showNotification(text)
    }
}

internal const val TODO_TITLE = "TODO_TITLE"