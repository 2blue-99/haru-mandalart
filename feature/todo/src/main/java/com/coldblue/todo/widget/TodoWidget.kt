package com.coldblue.todo.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.CheckboxDefaults
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.coldblue.data.R
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
                    titleBar = {
                        TitleBar(
                            title = "Todo",
                            startIcon = ImageProvider(R.drawable.notification_icon),
                        )
                    },
                    backgroundColor = GlanceTheme.colors.surface,
                ) {
//                    Column(GlanceModifier.fillMaxSize()) {
//                        Row(
//                            GlanceModifier.fillMaxWidth().padding(vertical = 4.dp),
//                            verticalAlignment = Alignment.Vertical.CenterVertically
//                        ) {
//                            StartIcon(ImageProvider(com.coldblue.data.R.drawable.notification_icon))
//                            Title("전체", {
//                                Logger.d("실행")
//                            })
//                        }
                    LazyColumn() {
                        itemsIndexed(todos) { index, it ->
                            val mandaKey =
                                mandaKeys.firstOrNull { key -> key.id - 1 == it.mandaIndex }
                            val color = indexToColor(mandaKey?.colorIndex ?: -1)

                            Column {
                                TodoWidgetItem(
                                    color = color,
                                    todo = it
                                ) {
                                    scope.launch {
                                        viewModel.upsertMandaTodo(
                                            it.copy(
                                                isDone = !it.isDone
                                            )
                                        )
//                                        TodoWidget().update(context, id)
                                    }
                                }
                                Spacer(modifier = GlanceModifier.padding(vertical = 3.dp))
                                if (index != todos.size-1) {
                                    Row(
                                        modifier = GlanceModifier.fillMaxWidth().height(0.5.dp)
                                            .background(HMColor.SubDarkText)
                                    ) {

                                    }
                                    Spacer(modifier = GlanceModifier.padding(vertical = 3.dp))
                                }
                            }
                        }
                    }
//                    }
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


    @Composable
    fun TodoWidgetItem(color: Color, todo: MandaTodo, onCheckedChange: () -> Unit) {
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
                checked = todo.isDone,
                onCheckedChange = {
                    onCheckedChange()
                }
            )
            Spacer(modifier = GlanceModifier.width(4.dp))
            Text(
                text = todo.title,
                style = TextStyle(color = GlanceTheme.colors.onSurface),
                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun StartIcon(
        startIcon: ImageProvider,
        iconColor: ColorProvider? = GlanceTheme.colors.onSurface,
    ) {
        Box(
            GlanceModifier.size(48.dp).padding(start = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = GlanceModifier.size(24.dp),
                provider = startIcon,
                contentDescription = "",
                colorFilter = iconColor?.let { ColorFilter.tint(iconColor) }
            )
        }
    }

    @Composable
    fun Title(
        title: String,
        onClick: () -> Unit,
        textColor: ColorProvider = GlanceTheme.colors.onSurface,
        fontFamily: FontFamily? = null,

        ) {
        Row(
            modifier = GlanceModifier.clickable { onClick() },
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontFamily = fontFamily
                ),
                maxLines = 1,
                modifier = GlanceModifier.defaultWeight()
            )
            StartIcon(ImageProvider(R.drawable.arrow_down))
        }

    }


}

