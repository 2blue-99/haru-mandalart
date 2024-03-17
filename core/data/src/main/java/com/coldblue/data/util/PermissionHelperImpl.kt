package com.coldblue.data.util

import com.coldblue.datastore.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PermissionHelperImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : PermissionHelper {

    override val noticePermissionRejectState: Flow<Boolean> = userDataSource.noticePermissionState


    override suspend fun updateNoticePermissionState(state: Boolean) {
        userDataSource.updateNoticePermissionState(state)
    }

}