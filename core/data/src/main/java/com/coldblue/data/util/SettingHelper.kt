package com.coldblue.data.util

interface SettingHelper {

    val versionName: String
    fun showOss()
    fun showContact()
    fun showPlayStore()
    fun checkAlarmPermission():Boolean
    fun showAppInfo()
}