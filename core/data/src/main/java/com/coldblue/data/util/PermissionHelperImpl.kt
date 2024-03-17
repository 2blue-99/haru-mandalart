package com.coldblue.data.util

import com.coldblue.datastore.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PermissionHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : PermissionHelper {

    override val noticePermissionState: Flow<Boolean> = userDataSource.noticePermissionState
    override val initPermissionState: Flow<Boolean> = userDataSource.initPermissionState


    override suspend fun updateNoticePermissionState(state: Boolean) {
        userDataSource.updateNoticePermissionState(state)
    }
    override suspend fun updateInitPermissionState(state: Boolean) {
        userDataSource.updateInitPermissionState(state)
    }


}