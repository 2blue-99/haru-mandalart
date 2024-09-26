package com.coldblue.data.receiver.notification

interface NotificationAppService {
    fun showAlarm(text: String)
    fun showNotification(text: String)
    fun isAppForeground(): Boolean
}
