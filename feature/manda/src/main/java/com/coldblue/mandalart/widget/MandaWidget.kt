package com.coldblue.mandalart.widget

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import com.coldblue.designsystem.R.drawable.round_add
import com.coldblue.designsystem.R.drawable.refresh
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.util.MandaUtils
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


fun mandaStateTpUIList(mandaState: MandaState): List<MandaType> {
    return when (mandaState) {
        is MandaState.Empty -> emptyList()
        is MandaState.Exist -> {
//            val d = mandaState.mandaUIList[0].mandaUI.name
//            Logger.d(d)
            mandaState.mandaUIList
        }
    }
}

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
                val scope = rememberCoroutineScope()

                var curMandaState by remember {
                    mutableStateOf(
                        when (val initManda = mandaStateList[4]) {
                            is MandaState.Empty -> emptyList()
                            is MandaState.Exist -> initManda.mandaUIList
                        }
                    )
                }
                var curId by remember { mutableIntStateOf(mandaStateList[4].id) }

                Scaffold(
                    modifier = GlanceModifier.fillMaxSize().clickable { viewModel.startApp() },
                    backgroundColor = GlanceTheme.colors.surface,

                    ) {
                    Column(
                        GlanceModifier.fillMaxSize(),
                        verticalAlignment = Alignment.Vertical.CenterVertically
                    ) {
                        Row( GlanceModifier.fillMaxWidth().padding(end = 12.dp), horizontalAlignment = Alignment.Horizontal.End) {
                            Box(
                                modifier = GlanceModifier.cornerRadius(12.dp)
                                    .background(GlanceTheme.colors.surface).clickable {
                                        curMandaState = when (val initManda = mandaStateList[4]) {
                                            is MandaState.Empty -> emptyList()
                                            is MandaState.Exist -> initManda.mandaUIList
                                        }
                                        curId = mandaStateList[4].id
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = GlanceModifier.size(24.dp),
                                    provider = ImageProvider(refresh),
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(ColorProvider(HMColor.Gray))
                                )
                            }
                        }
                        LazyVerticalGrid(
                            modifier = GlanceModifier.padding(16.dp),
                            gridCells = GridCells.Fixed(3),
                            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                        ) {
                            itemsIndexed(curMandaState) { index, manda ->
                                Box(GlanceModifier.padding(4.dp)) {
                                    when (manda) {
                                        is MandaType.None -> {
                                            EmptyBox { viewModel.startApp() }
                                        }

                                        is MandaType.Key -> {
                                            val color = manda.mandaUI.color
                                            val text = manda.mandaUI.name
                                            InBox(index + 1, color, text, onClick = { madnaId ->
                                                scope.launch {
                                                    curMandaState =
                                                        mandaStateTpUIList(mandaStateList.filter { it.id == madnaId }[0])
                                                    curId = madnaId
                                                    MandaWidget().update(context, id)
                                                }

                                            })
                                        }

                                        is MandaType.Detail -> {
                                            val color = manda.mandaUI.color
                                            val text = manda.mandaUI.name
                                            InBox(
                                                curId,
                                                color,
                                                text,
                                                onClick = { viewModel.startApp() })
                                        }
//                                        is MandaType.Detail -> {
//                                            val color = manda.mandaUI.color
//                                            val text = manda.mandaUI.name
//                                            InBox(curId, color, text, onClick = { id ->
//                                                tmp = mandaStateTpUIList(mandaStateList.first { it.id == id })
//                                                curId = id
//                                            })
//                                        }

//                                        is MandaType.Exist -> {
//                                            val color =
//                                                if (manda.id == 5) HMColor.LightPrimary else manda.mandaUIList.first().mandaUI.color
//                                            val text = manda.mandaUIList[4].mandaUI.name
//                                            InBox(manda.id, color, text, onClick = { id ->
//                                                tmp = mandaStateList.filter { it.id == id }
//                                            })
//                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun EmptyBox(
        onClick: () -> Unit
    ) {
        Box(
            modifier = GlanceModifier
                .size(90.dp).cornerRadius(14.dp)
                .background(HMColor.Gray) // 배경색 설정
                .padding(4.dp).clickable {
                    onClick()
                }, // 테두리를 흉내내기 위해 내부 패딩을 추가
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize().cornerRadius(12.dp) // 내부에 들어갈 내용이 차지하는 공간을 지정
                    .background(GlanceTheme.colors.surface) // 내부 Box 배경색 (테두리처럼 보임)
                , contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = GlanceModifier.size(50.dp),
                    provider = ImageProvider(round_add),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(ColorProvider(HMColor.Gray))
                )
            }
        }
    }

    @Composable
    fun InBox(id: Int, color: Color, name: String, onClick: (id: Int) -> Unit) {
        Box(
            modifier = GlanceModifier
                .size(90.dp).cornerRadius(14.dp)
                .background(color)
                .padding(4.dp).clickable {
                    onClick(id)
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize().cornerRadius(12.dp)
                    .background(GlanceTheme.colors.surface), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = GlanceModifier.padding(6.dp),
                    text = name, style = TextStyle(color = GlanceTheme.colors.onSurface)
                )

            }
        }
    }


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface MandaWidgetEntryPoint {
        fun getViewModel(): MandaWidgetViewModel
    }
}