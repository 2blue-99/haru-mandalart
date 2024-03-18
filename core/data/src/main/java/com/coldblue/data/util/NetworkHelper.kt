package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow

interface NetworkHelper {
    val isOnline: Flow<Boolean>
}