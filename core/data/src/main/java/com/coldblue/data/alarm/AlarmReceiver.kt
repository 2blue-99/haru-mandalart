package com.coldblue.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coldblue.data.repository.user.UserRepository
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var todoNotificationService: TodoNotificationService

    @Inject
    lateinit var userRepository: UserRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra(TODO_TITLE) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            if(userRepository.isAlarm.first())
                showNotification(text)
        }
    }
    private fun showNotification(text: String) {
        todoNotificationService.showNotification(text)
    }
}

internal const val TODO_TITLE = "TODO_TITLE"