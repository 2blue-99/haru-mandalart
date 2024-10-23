package com.coldblue.mandalart.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import com.coldblue.data.R
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.util.MandaUtils
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
        val mandaStateList = MandaUtils.transformToMandaList(
            viewModel.mandaKey.first(),
            viewModel.mandaDetail.first()
        )

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
//                    Column {
//                        repeat(3) { row ->
//                            Row {
//                                repeat(3) { column ->
//                                    when (mandaStateList[column + row * 3]) {
//                                        is MandaState.Empty -> {
//                                            EmptyBox()
//                                        }
//
//                                        is MandaState.Exist -> {
//                                            InBox()
//                                        }
//                                    }
//
//                                }
//                            }
//                        }
//                    }
                }
            }
        }

    }

//    @Composable
//    fun EmptyBox(
//    ) {
//        Box(
//            GlanceModifier.background(HMColor.Gray).padding(8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//
//        }
//    }
//
//    @Composable
//    fun InBox() {
//        Box(
//            GlanceModifier.background(HMColor.Primary).padding(8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//
//        }
//    }


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface MandaWidgetEntryPoint {
        fun getViewModel(): MandaWidgetViewModel
    }
}