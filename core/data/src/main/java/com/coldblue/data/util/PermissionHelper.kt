package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow

interface PermissionHelper {

    val noticePermissionRejectState: Flow<Boolean>

    suspend fun updateNoticePermissionState(state: Boolean)
}