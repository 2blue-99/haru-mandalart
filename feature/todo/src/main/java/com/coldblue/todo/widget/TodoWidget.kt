package com.coldblue.todo.widget

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.CheckboxDefaults
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaTodo
import com.orhanobut.logger.Logger
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TodoWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext
        val viewModel =
            EntryPoints.get(
                appContext,
                TodoWidgetEntryPoint::class.java,
            ).getTodoViewModel()

        provideContent {
            GlanceTheme {
                val isCheck = remember {
                    mutableStateOf(false)
                }
                val scope = rememberCoroutineScope()
                var todos by remember { mutableStateOf<List<MandaTodo>>(emptyList()) }
                LaunchedEffect(Unit) {
                    todos = viewModel.todo.first()
                }
//                val todos by viewModel.todos.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = GlanceModifier.clickable { viewModel.startApp() },
                    backgroundColor = GlanceTheme.colors.surface,
                    titleBar = {
                        TitleBar(
                            title = "Todo",
                            startIcon = ImageProvider(com.coldblue.data.R.drawable.notification_icon),
                        )
                    }
                ) {
                    LazyColumn() {
                        item {
                            Row() {
                                val checkText = if (isCheck.value) "Checked" else "Unchecked"
                                CheckBox(
                                    modifier = GlanceModifier.padding(16.dp),
                                    checked = isCheck.value,
                                    onCheckedChange = {
                                        isCheck.value = !isCheck.value
                                    },
                                    text = checkText
                                )
                            }
                        }
                        items(todos) {
                            Row {
                                CheckBox(
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = ColorProvider(
                                            day = HMColor.Primary,
                                            night = HMColor.Primary
                                        ),
                                        uncheckedColor = ColorProvider(
                                            day = HMColor.Primary,
                                            night = HMColor.Primary
                                        )
                                    ),
                                    checked = it.isDone,
                                    onCheckedChange = {
//                                        actionRunCallback(UpsertTodoActionCallback::class.java)
                                        scope.launch {
                                            viewModel.upsertMandaTodo(
                                                it.copy(
                                                    isDone = !it.isDone
                                                )
                                            )
                                            TodoWidget().update(context, id)
                                            Logger.d("업데이트 요청")
                                        }
                                    }
                                )
                                Text(
                                    text = it.title,
                                    style = TextStyle(color = GlanceTheme.colors.onSurface)
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface TodoWidgetEntryPoint {
        fun getTodoViewModel(): TodoWidgetViewModel
    }

    object UpsertTodoActionCallback : ActionCallback {
        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
            TodoWidget().update(context, glanceId)
        }

    }
}
