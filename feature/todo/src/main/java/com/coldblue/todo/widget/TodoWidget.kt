package com.coldblue.todo.widget

import android.content.Context
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.CheckboxDefaults
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaTodo
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
                val scope = rememberCoroutineScope()
                val todos by viewModel.todos.collectAsState(emptyList<MandaTodo>())
                val mandaKeys by viewModel.mandaKeys.collectAsState(emptyList())

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
                        items(todos) {
                            val mandaKey = mandaKeys.firstOrNull { key -> key.id-1 == it.mandaIndex }
                            val color = indexToColor(mandaKey?.colorIndex ?: -1)

                            Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                                CheckBox(
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = ColorProvider(
                                            day = color,
                                            night = color
                                        ),
                                        uncheckedColor = ColorProvider(
                                            day = color,
                                            night = color
                                        )
                                    ),
                                    checked = it.isDone,
                                    onCheckedChange = {
                                        scope.launch {
                                            viewModel.upsertMandaTodo(
                                                it.copy(
                                                    isDone = !it.isDone
                                                )
                                            )
                                            TodoWidget().update(context, id)
                                        }
                                    }
                                )
                                Spacer(modifier = GlanceModifier.width(4.dp))
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

    private fun indexToColor(index: Int): Color {
        return when (index) {
            0 -> HMColor.DarkPastel.Pink
            1 -> HMColor.DarkPastel.Red
            2 -> HMColor.DarkPastel.Orange
            3 -> HMColor.DarkPastel.Yellow
            4 -> HMColor.DarkPastel.Green
            5 -> HMColor.DarkPastel.Blue
            6 -> HMColor.DarkPastel.Mint
            7 -> HMColor.DarkPastel.Purple
            else -> HMColor.Gray
        }
    }

}
