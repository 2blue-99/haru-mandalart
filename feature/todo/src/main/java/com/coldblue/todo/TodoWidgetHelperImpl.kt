package com.coldblue.todo

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.coldblue.data.sync.TodoWidgetHelper
import com.coldblue.todo.widget.TodoWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class TodoWidgetHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TodoWidgetHelper {
    override suspend fun widgetUpdate() {
        TodoWidget().updateAll(context)
    }
}