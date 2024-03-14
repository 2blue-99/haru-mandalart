package com.coldblue.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.coldblue.data.sync.worker.SyncReadWorker
import com.coldblue.data.sync.worker.SyncWriteWorker
import com.coldblue.database.entity.SyncableEntity
import com.coldblue.datastore.UpdateTimeDataSource
import com.coldblue.datastore.UserDataSource
import com.orhanobut.logger.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SyncHelper {
    private val SYNC_WRITE = "SyncWrite"
    private val SYNC_READ = "SyncRead"

    private val syncConstraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    private fun writeRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SyncWriteWorker>().addTag(SYNC_WRITE)
            .setConstraints(syncConstraints)
            .build()
    }

    private fun readRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
            .setConstraints(syncConstraints)
            .build()
    }

    private fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .setConstraints(syncConstraints)
        .build()

    override suspend fun setMaxUpdateTime(
        data: List<SyncableEntity>,
        updateTime: suspend (String) -> Unit
    ) {
        val maxUpdateTime = data.maxOfOrNull { it.updateTime }
        maxUpdateTime?.run { updateTime(this) }
    }

    override fun syncWrite() {
        Logger.d("쓰기 요청")
        WorkManager.getInstance(context).beginUniqueWork(
            SYNC_READ + SYNC_WRITE,
            ExistingWorkPolicy.KEEP,
            readRequest()
        ).then(writeRequest()).enqueue()
    }

    override fun initialize() {
        WorkManager.getInstance(context).enqueueUniqueWork(
            SYNC_READ,
            ExistingWorkPolicy.KEEP,
            startUpSyncWork()
        )
    }

    override suspend fun <T> toSyncData(
        getToSyncData: suspend (String) -> List<T>,
        updateTime: String
    ): List<T> {
        return getToSyncData(updateTime)
    }


}