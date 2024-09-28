package com.coldblue.haru_mandalart.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaKeyRepository
import com.coldblue.data.repository.todo.MandaTodoRepository
import com.coldblue.data.repository.user.UserRepository
import com.coldblue.data.sync.worker.SyncReadWorker
import com.coldblue.data.sync.worker.SyncWriteWorker
import com.coldblue.haru_mandalart.notification.NotificationAppServiceImpl
import com.google.firebase.FirebaseOptions
import com.google.firebase.initialize
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HMApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: SyncWorkerFactory

    val lifecycleObserver = AppLifecycleObserver()

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(workerFactory)
                .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
        setFirebase()
        initLogger()
    }

    class SyncWorkerFactory @Inject constructor(
        private val mandaKeyRepository: MandaKeyRepository,
        private val mandaDetailRepository: MandaDetailRepository,
        private val userRepository: UserRepository,
        private val mandaTodoRepository: MandaTodoRepository,

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
                    mandaKeyRepository,
                    mandaDetailRepository,
                    mandaTodoRepository,
                    userRepository,
                )

                SyncWriteWorker::class.java.name -> SyncWriteWorker(
                    appContext,
                    workerParameters,
                    mandaKeyRepository,
                    mandaDetailRepository,
                    mandaTodoRepository,
                    userRepository
                )

                else -> null
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationAppServiceImpl.CHANNEL_ID,
            NotificationAppServiceImpl.CHANNEL_NAME,
            NotificationAppServiceImpl.IMPORTANCE,
        )
        channel.description = NotificationAppServiceImpl.description

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

    private fun setFirebase(){
        val options = FirebaseOptions.Builder()
            .setProjectId("com.coldblue.haru_mandalart")
            .setApplicationId("1:243006459444:android:7b04ad747fe51467cf8461")
            .setApiKey("AIzaSyAGNAWI8GtxwqtTn7OGfbwFSHi8N4NGwm8")
            .build()
        com.google.firebase.Firebase.initialize(this, options)
    }
}