package com.coldblue.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coldblue.data.sync.SyncReadHelper.SYNC_READ
import com.coldblue.data.sync.worker.SyncReadWorker
import com.coldblue.data.sync.worker.SyncWriteWorker
import com.orhanobut.logger.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SyncWriteHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncWriteHelper {
    private val SYNC_WRITE = "SyncWrite"
    private val syncWriteConstraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    private fun writeRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SyncWriteWorker>().addTag(SYNC_WRITE)
            .setConstraints(syncWriteConstraints)
            .build()
    }

    override fun syncWrite() {
        Logger.d("쓰기 요청")

        WorkManager.getInstance(context).beginUniqueWork(
            SYNC_READ + SYNC_WRITE,
            ExistingWorkPolicy.KEEP,
            SyncReadHelper.readRequest()
        ).then(writeRequest()).enqueue()
    }
}