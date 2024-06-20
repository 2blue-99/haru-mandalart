package com.coldblue.todo

import android.content.Context
import com.coldblue.data.sync.TodoWidgetHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoWidgetModule {
    @Singleton
    @Provides
    fun provideWidgetHelper(@ApplicationContext context: Context): TodoWidgetHelper =
        TodoWidgetHelperImpl(context)


}
