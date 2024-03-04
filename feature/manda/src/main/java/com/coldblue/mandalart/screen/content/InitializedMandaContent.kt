package com.coldblue.mandalart.screen.content

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
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
import com.coldblue.mandalart.model.MandaUI
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

    val animateDonePercentage = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )

    LaunchedEffect(Unit) { percentage = uiState.donePercentage }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(16.dp)
    ) {
        item { HMTitleComponent() }

        item {
            MandaStatus(
                finalName = uiState.finalName,
                keyMandaCnt = uiState.keyMandaCnt,
                detailMandaCnt = uiState.detailMandaCnt,
                donePercentage = uiState.donePercentage,
                animateDonePercentage = animateDonePercentage.value
            )
        }

        item {
            Mandalart(
                mandaStateList = uiState.mandaStateList,
            )
        }
    }
}

@Composable
fun MandaStatus(
    finalName: String,
    keyMandaCnt: Int,
    detailMandaCnt: Int,
    donePercentage: Float,
    animateDonePercentage: Float
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            FilledIconButton(
                modifier = Modifier.size(30.dp),
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = HMColor.Primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "\" $finalName \"",
            style = HmStyle.text24,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(text = "핵심 목표 : ", style = HmStyle.text16)
                Text(
                    text = "$keyMandaCnt / $MAX_MANDA_KEY_SIZE",
                    style = HmStyle.text16
                )
            }
            Row {
                Text(text = "세부 목표 : ", style = HmStyle.text16)
                Text(
                    text = "$detailMandaCnt / $MAX_MANDA_DETAIL_SIZE",
                    style = HmStyle.text16
                )
            }
        }
        Text(
            text = "달성률 ${(donePercentage * 100).roundToInt()} %",
            style = HmStyle.text12,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(
            progress = { animateDonePercentage },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = HMColor.Primary,
            trackColor = HMColor.Gray
        )
    }
}

@Composable
fun Mandalart(
    mandaStateList: List<MandaState>,
) {
    var translationX by remember { mutableFloatStateOf(1f) }
    var translationY by remember { mutableFloatStateOf(1f) }
    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var zoomState by remember { mutableStateOf(false) }
    var pivotX by remember { mutableFloatStateOf(0f) }
    var pivotY by remember { mutableFloatStateOf(0f) }


    val dampingRatio = 1f // 클수록 스프링 효과 작음
    val stiffness = 1000f // 클수록 빨리 확대, 축소 됨


    val animatedTranslationX by animateFloatAsState(
        targetValue = translationX,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )
    val animatedTranslationY by animateFloatAsState(
        targetValue = translationY,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )
    val animatedScaleX by animateFloatAsState(
        targetValue = scaleX,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )
    val animatedScaleY by animateFloatAsState(
        targetValue = scaleY,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )

    fun zoomInAndOut(index: Int) {
        Log.e("TAG", "zoomInAndOut: $index", )
        when (index) {
            0, 1, 3, 4 -> {
                pivotX = 0f
                pivotY = 0f
                scaleX += 0.5f
                scaleY += 0.5f
                zoomState = true
            }

            2, 5 -> {
                pivotX = 1f
                pivotY = 0f
                scaleX += 0.5f
                scaleY += 0.5f
                zoomState = true
            }

            6, 7 -> {
                pivotX = 0f
                pivotY = 1f
                scaleX += 0.5f
                scaleY += 0.5f
                zoomState = true
            }

            8 -> {
                pivotX = 1f
                pivotY = 1f
                scaleX += 0.5f
                scaleY += 0.5f
                zoomState = true
            }
            else -> {
                scaleX -= 0.5f
                scaleY -= 0.5f
                zoomState = false
            }
        }
    }


    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                modifier = Modifier.size(30.dp),
                onClick = {
                    if (zoomState) {
                        zoomInAndOut(-1)
                    }
                }
            ) {
                Text(text = "C")
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .graphicsLayer(
                    translationX = animatedTranslationX,
                    translationY = animatedTranslationY,
                    scaleX = animatedScaleX.coerceIn(0.5f, 1.5f),
                    scaleY = animatedScaleY.coerceIn(0.5f, 1.5f),
                    clip = true,
                    transformOrigin = TransformOrigin(pivotX, pivotY)
                )
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(HMColor.Background)
                .onGloballyPositioned {
//                    mandaWidth = it.size.width.toFloat()
//                    mandaHeight = it.size.height.toFloat()
                }
        ) {
            repeat(3) { keyRow ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                ) {
                    repeat(3) { keyColumn ->
                        when (val state = mandaStateList[keyColumn + ((keyRow) * 3)]) {
                            is MandaState.Empty -> {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 5.dp)
                                        .clickable {
                                            if (!zoomState) {
                                                zoomInAndOut(keyColumn + (keyRow * 3))
                                            }
                                        }
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
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp)
                                        .clickable {
                                            if (!zoomState) {
                                                zoomInAndOut(keyColumn + (keyRow * 3))
                                                zoomState = true
                                            }
                                        }
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
}

@Preview
@Composable
fun MandaContentPreview() {
    val stateList = mutableListOf<MandaState>()
    val typeList = mutableListOf<MandaType>()
    repeat(9) { cnt ->
        if (cnt == 8) {
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
        } else {
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
            typeList.add(MandaType.None(MandaUI(id = 0)))
        }
        stateList.add(
            MandaState.Exist(
                mandaUIList = typeList.toList()
            )
        )
        typeList.clear()
    }
    Mandalart(mandaStateList = stateList)
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