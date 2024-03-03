package com.coldblue.mandalart.screen.content

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMMandaEmptyButton
import com.coldblue.designsystem.component.HMMandaFillButton
import com.coldblue.designsystem.component.HMMandaOutlineButton
import com.coldblue.designsystem.component.HMTitleComponent
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.state.MandaUIState
import kotlin.math.roundToInt

const val MAX_MANDA_KEY_SIZE = 8
const val MAX_MANDA_DETAIL_SIZE = 64

@Composable
fun InitializedMandaContent(
    uiState: MandaUIState.InitializedSuccess
) {
    var percentage by remember { mutableFloatStateOf(0f) }

    val animatedFloatColor = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )

    LaunchedEffect(Unit) {
        percentage = uiState.donePercentage
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item { HMTitleComponent() }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                FilledIconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = HMColor.Primary)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "\" ${uiState.finalName} \"",
                style = HmStyle.text24,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "핵심 목표 : ", style = HmStyle.text16)
                    Text(
                        text = "${uiState.keyMandaCnt} / $MAX_MANDA_KEY_SIZE",
                        style = HmStyle.text16
                    )
                }
                Row {
                    Text(text = "세부 목표 : ", style = HmStyle.text16)
                    Text(
                        text = "${uiState.detailMandaCnt} / $MAX_MANDA_DETAIL_SIZE",
                        style = HmStyle.text16
                    )
                }
            }
        }
        item {
            Text(
                text = "달성률 ${(uiState.donePercentage * 100).roundToInt()} %",
                style = HmStyle.text12,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(5.dp))

            LinearProgressIndicator(
                progress = { animatedFloatColor.value },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = HMColor.Primary,
                trackColor = HMColor.Gray
            )
        }

        item {
            MandaContent(
                mandaStateList = uiState.mandaStateList
            )
        }
    }
}

@Composable
fun MandaContent(
    mandaStateList: List<MandaState>,
) {
    Log.e("TAG", "MandaContent: $mandaStateList")
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(Color.Companion.White)
            .fillMaxWidth()
    ) {
        repeat(3){keyRow ->
            Row(modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth()) {
                repeat(3) { keyColumn ->
                    when (val state = mandaStateList[(keyColumn) + ((keyRow) * 3)]) {
                        is MandaState.Empty -> {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 5.dp)
                            ) {
                                HMMandaEmptyButton()
                            }
                        }
                        is MandaState.Exist -> {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.Companion.White)
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp)
                            ) {
                                repeat(3) { detailRow ->
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        repeat(3) { detailColumn ->
                                            when (val type =
                                                state.mandaUIList[(detailColumn) + ((detailRow) * 3)]) {
                                                is MandaType.None ->
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .aspectRatio(1F)
                                                            .padding(2.dp)
                                                    ) {
                                                        HMMandaEmptyButton()
                                                    }

                                                is MandaType.KeyStart ->
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .aspectRatio(1F)
                                                            .padding(2.dp)
                                                    ) {
                                                        HMMandaOutlineButton(
                                                            name = type.mandaUI.name,
                                                            outlineColor = type.mandaUI.darkColor
                                                        ) {}
                                                    }


                                                is MandaType.DetailStart ->
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .aspectRatio(1F)
                                                            .padding(2.dp)
                                                    ) {
                                                        HMMandaFillButton(
                                                            name = type.mandaUI.name,
                                                            backgroundColor = type.mandaUI.lightColor,
                                                            textColor = HMColor.Text
                                                        ) {}
                                                    }


                                                is MandaType.Done ->
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .aspectRatio(1F)
                                                            .padding(2.dp)
                                                    ) {
                                                        HMMandaFillButton(
                                                            name = type.mandaUI.name,
                                                            backgroundColor = type.mandaUI.darkColor,
                                                            textColor = HMColor.Background
                                                        ) {}
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MandaContentPreview() {
//    val stateList = mutableListOf<MandaState>()
//    val typeList = mutableListOf<MandaType>()
//    repeat(9) { cnt ->
//        if(cnt == 8){
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//            typeList.add(MandaType.DetailStart(name = "TEST", id = 0))
//        }else {
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//            typeList.add(MandaType.None(id = 0))
//        }
//        stateList.add(
//            MandaState.Exist(
//                darkColor = HMColor.Dark.Pink,
//                lightColor = HMColor.Light.Pink,
//                mandaUIList = typeList.toList()
//            )
//        )
//        typeList.clear()
//    }
//    MandaContent(mandaStateList = stateList)
}

//@Preview
//@Composable
//fun MandaContentPreview() {
//    val stateList = mutableListOf<MandaState>()
//    val typeList = mutableListOf<MandaType>()
//    repeat(9) { cnt ->
////        repeat(9){it ->
////            typeList.add(MandaType.Empty(id = 0 ))
////        }
//        stateList.add(MandaState.Empty(id = 0))
//    }
//    MandaContent(mandaStateList = stateList)
//}