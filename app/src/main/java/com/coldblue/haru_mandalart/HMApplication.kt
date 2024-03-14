package com.coldblue.haru_mandalart

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.data.repository.todo.TodoGroupRepository
import com.coldblue.data.repository.todo.TodoRepository
import com.coldblue.data.sync.worker.SyncReadWorker
import com.coldblue.data.sync.worker.SyncWriteWorker
import com.coldblue.haru_mandalart.notification.TodoNotificationServiceImpl
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.assisted.Assisted
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HMApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: SyncWorkerFactory
    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(workerFactory)
                .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initLogger()
    }

    class SyncWorkerFactory @Inject constructor(
        private val todoRepository: TodoRepository,
        private val todoGroupRepository: TodoGroupRepository,
        private val currentGroupRepository: CurrentGroupRepository,

        ) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return when (workerClassName) {
                SyncReadWorker::class.java.name -> SyncReadWorker(
                    appContext,
                    workerParameters,
                    todoRepository,
                    todoGroupRepository,
                    currentGroupRepository
                )

                SyncWriteWorker::class.java.name -> SyncWriteWorker(
                    appContext,
                    workerParameters,
                    todoRepository,
                    todoGroupRepository,
                    currentGroupRepository
                )

                else -> null
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TodoNotificationServiceImpl.CHANNEL_ID,
            TodoNotificationServiceImpl.CHANNEL_NAME,
            TodoNotificationServiceImpl.IMPORTANCE,
        )
        channel.description = TodoNotificationServiceImpl.description

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initLogger() {
        val strategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("logger").showThreadInfo(false).methodCount(1)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(strategy))
    }
}