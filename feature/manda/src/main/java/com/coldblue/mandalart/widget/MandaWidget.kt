package com.coldblue.mandalart.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.coldblue.data.R
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first

class MandaWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext
        val viewModel =
            EntryPoints.get(
                appContext,
                MandaWidgetEntryPoint::class.java,
            ).getViewModel()
        val mandaKey = viewModel.mandaKey.first()

        provideContent {
            GlanceTheme {
                Scaffold(
                    modifier = GlanceModifier.clickable { viewModel.startApp() },
                    backgroundColor = GlanceTheme.colors.surface,
                    titleBar = {
                        TitleBar(
                            title = "하루 만다라트",
                            startIcon = ImageProvider(R.drawable.notification_icon),
                        )
                    }
                ) {
                    LazyColumn() {
                        items(mandaKey) {
                            Text(
                                text = it.name,
                                style = TextStyle(color = GlanceTheme.colors.onSurface)
                            )
                        }
                    }
                }
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface MandaWidgetEntryPoint {
        fun getViewModel(): MandaWidgetViewModel
    }
}