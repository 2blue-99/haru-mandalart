package com.coldblue.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.coldblue.data.receiver.notification.TodoNotificationService
import com.coldblue.data.repository.user.UserRepository
import com.coldblue.database.dao.NotificationDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var todoNotificationService: TodoNotificationService

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var notificationDao: NotificationDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra(TODO_TITLE) ?: return
        val id = intent.getIntExtra(TODO_ID, 0)

        CoroutineScope(Dispatchers.IO).launch {
            notificationDao.deleteNotification(id)
            if (userRepository.isAlarm.first())
                showNotification(text)
        }
    }

    private fun showNotification(text: String) {
        todoNotificationService.showNotification(text)
    }
}

internal const val TODO_TITLE = "TODO_TITLE"
internal const val TODO_ID = "TODO_ID"