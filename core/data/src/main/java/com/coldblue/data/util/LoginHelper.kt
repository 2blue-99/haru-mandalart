package com.coldblue.data.util

import kotlinx.coroutines.flow.Flow

interface LoginHelper {
    val isLogin: Flow<Boolean>
}