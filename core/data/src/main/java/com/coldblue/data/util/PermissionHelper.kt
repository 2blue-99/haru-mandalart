package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow

interface PermissionHelper {

    val noticePermissionState: Flow<Boolean>
    val initPermissionState: Flow<Boolean>
    suspend fun updateNoticePermissionState(state: Boolean)
    suspend fun updateInitPermissionState(state: Boolean)
}