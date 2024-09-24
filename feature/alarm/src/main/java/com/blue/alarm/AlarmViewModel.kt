//package com.blue.alarm
//
//import androidx.lifecycle.ViewModel
//import com.coldblue.data.receiver.alarm.AlarmScheduler
//import com.coldblue.model.NotificationAlarmItem
//import dagger.hilt.android.lifecycle.HiltViewModel
//import java.time.LocalDateTime
//import javax.inject.Inject
//
//@HiltViewModel
//class AlarmViewModel @Inject constructor (
//    private val alarmScheduler: AlarmScheduler
//): ViewModel() {
//    fun addAlarm(time: LocalDateTime, title: String, alarmCode : Int){
//        alarmScheduler.addAlarm(NotificationAlarmItem(time = time, title = title, id = alarmCode))
//    }
//
//    fun cancelAlarm(alarmCode: Int){
//        alarmScheduler.cancelAlarm(alarmCode)
//    }
//}