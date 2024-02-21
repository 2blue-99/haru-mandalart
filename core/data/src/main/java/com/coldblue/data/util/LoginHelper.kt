package com.coldblue.data.util

interface LoginHelper {
    suspend fun getUserLoginState(): Boolean
}