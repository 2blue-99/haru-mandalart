package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow

interface PermissionHelper {

    val initPermissionState: Flow<Boolean>
    suspend fun updateInitPermissionState(state: Boolean)
}