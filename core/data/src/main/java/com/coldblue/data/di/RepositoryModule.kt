package com.coldblue.data.di

import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaDetailRepositoryImpl
import com.coldblue.data.repository.manda.MandaKeyRepository
import com.coldblue.data.repository.manda.MandaKeyRepositoryImpl
import com.coldblue.data.repository.manda.UpdateNoteRepository
import com.coldblue.data.repository.manda.UpdateNoteRepositoryImpl
import com.coldblue.data.repository.notice.NoticeRepository
import com.coldblue.data.repository.notice.NoticeRepositoryImpl
import com.coldblue.data.repository.survey.SurveyRepository
import com.coldblue.data.repository.survey.SurveyRepositoryImpl
import com.coldblue.data.repository.todo.CurrentGroupRepository
import com.coldblue.data.repository.todo.CurrentGroupRepositoryImpl
import com.coldblue.data.repository.todo.TodoGroupRepository
import com.coldblue.data.repository.todo.TodoGroupRepositoryImpl
import com.coldblue.data.repository.todo.TodoRepository
import com.coldblue.data.repository.todo.TodoRepositoryImpl
import com.coldblue.data.repository.user.UserRepository
import com.coldblue.data.repository.user.UserRepositoryImpl
import com.coldblue.data.util.ConnectivityManagerNetworkHelper
import com.coldblue.data.util.LoginHelper
import com.coldblue.data.util.LoginHelperImpl
import com.coldblue.data.util.NetworkHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindMandaKeyRepo(
        mandaRepo: MandaKeyRepositoryImpl
    ): MandaKeyRepository

    @Binds
    fun bindMandaDetailRepo(
        mandaRepo: MandaDetailRepositoryImpl
    ): MandaDetailRepository

    @Binds
    fun bindsUpdateNoteRepo(
        updateNoteRepo: UpdateNoteRepositoryImpl
    ): UpdateNoteRepository

    @Binds
    fun bindTodoRepository(
        todoRepository: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    fun bindCurrentGroupRepository(
        currentGroupRepository: CurrentGroupRepositoryImpl
    ): CurrentGroupRepository
    @Binds
    fun bindTodoGroupRepository(
        todoGroupRepository: TodoGroupRepositoryImpl
    ): TodoGroupRepository
    @Binds
    fun bindNoticeRepository(
        noticeRepository: NoticeRepositoryImpl
    ): NoticeRepository

    @Binds
    fun bindSurveyRepository(
        surveyRepository: SurveyRepositoryImpl
    ): SurveyRepository

    @Binds
    fun bindUserPreferencesRepo(
        userPreferencesRepo: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindLoginHelperRepo(
        loginHelper: LoginHelperImpl
    ): LoginHelper

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkHelper
    ): NetworkHelper


}