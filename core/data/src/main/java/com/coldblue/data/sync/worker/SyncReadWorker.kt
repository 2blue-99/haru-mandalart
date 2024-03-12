package com.coldblue.data.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.coldblue.data.repository.TodoRepository
import com.coldblue.data.sync.SyncReadHelper.SYNC_READ
import com.coldblue.data.sync.SyncReadHelper.syncReadConstraints
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


@HiltWorker
class SyncReadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,

    ) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val syncedSucceed = awaitAll(
                async { todoRepository.syncRead() },
            ).all { it }
            if (syncedSucceed) {
                Logger.d("리드 실행성공")
                Result.success()
            } else {
                Logger.e("리드 실행 다시")
                Result.retry()
            }
        } catch (e: Exception) {
            Logger.e("리드 실행 실패")
            Result.failure()
        }
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncReadWorker>().addTag(SYNC_READ)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(syncReadConstraints)
            .build()
    }
}