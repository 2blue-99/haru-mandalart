package com.coldblue.mandalart.screen.content

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMTitleComponent
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.iconpack.ZoomIn
import com.coldblue.designsystem.iconpack.ZoomOut
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.MandaBottomSheet
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.orhanobut.logger.Logger
import kotlin.math.abs
import kotlin.math.roundToInt

const val MAX_MANDA_KEY_SIZE = 8
const val MAX_MANDA_DETAIL_SIZE = 64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitializedMandaContent(
    uiState: MandaUIState.InitializedSuccess,
    mandaBottomSheetUIState: MandaBottomSheetUIState,
    upsertMandaFinal: (String) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    deleteMandaAll: () -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit
) {
    var percentage by remember { mutableFloatStateOf(0f) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val animateDonePercentage = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )

    if (mandaBottomSheetUIState is MandaBottomSheetUIState.Up) {
        MandaBottomSheet(
            mandaBottomSheetContentState = mandaBottomSheetUIState.mandaBottomSheetContentState,
            sheetState = sheetState,
            upsertMandaFinal = upsertMandaFinal,
            upsertMandaKey = upsertMandaKey,
            upsertMandaDetail = upsertMandaDetail,
            deleteMandaKey = deleteMandaKey,
            deleteMandaDetail = deleteMandaDetail
        ) { changeBottomSheet(false, null) }
    }

    LaunchedEffect(uiState.donePercentage) { percentage = uiState.donePercentage }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HMTitleComponent()

        MandaStatus(
            finalName = uiState.finalName,
            keyMandaCnt = uiState.keyMandaCnt,
            detailMandaCnt = uiState.detailMandaCnt,
            donePercentage = uiState.donePercentage,
            animateDonePercentage = animateDonePercentage.value
        ) {
            changeBottomSheet(
                true,
                MandaBottomSheetContentState.Update(MandaBottomSheetContentType.MandaFinal(mandaUI = it))
            )
        }

        Mandalart(
            mandaStateList = uiState.mandaStateList,
            changeBottomSheet = changeBottomSheet
        )
    }
}

@Composable
fun MandaStatus(
    finalName: String,
    keyMandaCnt: Int,
    detailMandaCnt: Int,
    donePercentage: Float,
    animateDonePercentage: Float,
    onClickTitle: (MandaUI) -> Unit
) {


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Box(
//            modifier = Modifier.fillMaxWidth(),
//            contentAlignment = Alignment.CenterEnd
//        ) {
//            FilledIconButton(
//                modifier = Modifier.size(30.dp),
//                onClick = { /*TODO*/ },
//                colors = IconButtonDefaults.filledIconButtonColors(containerColor = HMColor.Primary)
//            ) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = "")
//            }
//        }
        ClickableText(
            text = AnnotatedString("\" $finalName \""),
            onClick = { onClickTitle(MandaUI(id = 4, name = finalName)) },
            style = HmStyle.text24,
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
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit
) {
    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var pivotX by remember { mutableFloatStateOf(0f) }
    var pivotY by remember { mutableFloatStateOf(0f) }
    var zoomState by remember { mutableStateOf(false) }
    var beforeDirection by remember { mutableIntStateOf(-1) }
    var afterDirection by remember { mutableIntStateOf(-1) }

    val dampingRatio = 1f // 클수록 스프링 효과 감소
    val stiffness = 1000f // 클수록 빨리 확대, 축소

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
        Log.e("TAG", "zoomInAndOut: $index")
        when (index) {
            0, 1, 3, 4 -> {
                pivotX = 0f
                pivotY = 0f
                scaleX = 1.5f
                scaleY = 1.5f
                zoomState = true
                beforeDirection = 0

            }

            2, 5 -> {
                pivotX = 1f
                pivotY = 0f
                scaleX = 1.5f
                scaleY = 1.5f
                zoomState = true
                beforeDirection = 1
            }

            6, 7 -> {
                pivotX = 0f
                pivotY = 1f
                scaleX = 1.5f
                scaleY = 1.5f
                zoomState = true
                beforeDirection = 2
            }

            8 -> {
                pivotX = 1f
                pivotY = 1f
                scaleX = 1.5f
                scaleY = 1.5f
                zoomState = true
                beforeDirection = 3
            }

            // 축소
            else -> {
                scaleX = 1.0f
                scaleY = 1.0f
                zoomState = false
            }
        }
    }

    fun dragStartDetector(dragAmount: Offset) {
        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            when {
                x > 0 -> {  // left
                    if (beforeDirection == 1)
                        afterDirection = 0
                    else if (beforeDirection == 3)
                        afterDirection = 2
                }

                x < 0 -> {  // right
                    if (beforeDirection == 0)
                        afterDirection = 1
                    else if (beforeDirection == 2)
                        afterDirection = 3
                }
            }
        } else {
            when {
                y > 0 -> {    // Top
                    if (beforeDirection == 2)
                        afterDirection = 0
                    else if (beforeDirection == 3)
                        afterDirection = 1
                }

                y < 0 -> {  // Bottom
                    if (beforeDirection == 0)
                        afterDirection = 2
                    else if (beforeDirection == 1)
                        afterDirection = 3
                }
            }
        }
    }

    fun dragEndDetector() {
        when (afterDirection) {
            0 -> {
                if (beforeDirection == 1)
                    zoomInAndOut(0)
                else if (beforeDirection == 2)
                    zoomInAndOut(0)
            }

            1 -> {
                if (beforeDirection == 0)
                    zoomInAndOut(2)
                else if (beforeDirection == 3)
                    zoomInAndOut(2)
            }

            2 -> {
                if (beforeDirection == 0)
                    zoomInAndOut(6)
                else if (beforeDirection == 3)
                    zoomInAndOut(6)
            }

            3 -> {
                if (beforeDirection == 1)
                    zoomInAndOut(8)
                else if (beforeDirection == 2)
                    zoomInAndOut(8)
            }
        }
    }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = {
                    if (zoomState)
                        zoomInAndOut(-1)
                    else
                        zoomInAndOut(1)
                }
            ) {
                if (zoomState)
                    Icon(
                        imageVector = IconPack.ZoomOut,
                        tint = HMColor.Primary,
                        contentDescription = ""
                    )
                else
                    Icon(
                        imageVector = IconPack.ZoomIn,
                        tint = HMColor.Primary,
                        contentDescription = ""
                    )
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = animatedScaleX.coerceIn(0.5f, 1.5f),
                            scaleY = animatedScaleY.coerceIn(0.5f, 1.5f),
                            transformOrigin = TransformOrigin(pivotX, pivotY)
                        )
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragStartDetector(dragAmount)
                                },
                                onDragEnd = {
                                    dragEndDetector()
                                }
                            )
                        }
                ) {
                    repeat(3) { keyRow ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        ) {
                            repeat(3) { keyColumn ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 5.dp)
                                ) {
                                    when (val bigBox = mandaStateList[keyColumn + keyRow * 3]) {
                                        is MandaState.Empty -> {
                                            Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                MandaEmptyBox {
                                                    changeBottomSheet(
                                                        true,
                                                        MandaBottomSheetContentState.Insert(
                                                            MandaBottomSheetContentType.MandaKey(
                                                                MandaUI(id = bigBox.id),
                                                                null
                                                            )
                                                        )
                                                    )
                                                }
                                            }
                                        }

                                        is MandaState.Exist -> {
                                            Column(
                                                horizontalAlignment = Alignment.Start,
                                                verticalArrangement = Arrangement.Top,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                repeat(3) { detailRow ->

                                                    Row {
                                                        repeat(3) { detailColumn ->
                                                            when (val smallBox =
                                                                bigBox.mandaUIList[detailColumn + detailRow * 3]) {
                                                                is MandaType.None -> {
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        MandaEmptyBox(
                                                                            color = smallBox.mandaUI.darkColor
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Insert(
                                                                                    MandaBottomSheetContentType.MandaDetail(
                                                                                        smallBox.mandaUI,
                                                                                    )
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }

                                                                is MandaType.Key -> {
                                                                    val data = smallBox.mandaUI
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        MandaKeyBox(
                                                                            name = data.name,
                                                                            color = data.darkColor,
                                                                            isDone = data.isDone
                                                                        ) {
                                                                            Log.e(
                                                                                "TAG",
                                                                                "Mandalart: $data",

                                                                            )
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Update(
                                                                                    if (bigBox.id == 5) {
                                                                                        MandaBottomSheetContentType.MandaFinal(
                                                                                            data
                                                                                        )
                                                                                    } else {
                                                                                        MandaBottomSheetContentType.MandaKey(
                                                                                            data,
                                                                                            smallBox.groupIdList
                                                                                        )
                                                                                    }
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }

                                                                is MandaType.Detail -> {
                                                                    val data = smallBox.mandaUI
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        MandaDetailBox(
                                                                            name = data.name,
                                                                            darkColor = data.darkColor,
                                                                            lightColor = data.lightColor,
                                                                            isDone = data.isDone
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Update(
                                                                                    MandaBottomSheetContentType.MandaDetail(
                                                                                        data
                                                                                    )
                                                                                )
                                                                            )
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
                                    if (!zoomState) {
                                        Spacer(modifier = Modifier
                                            .background(Color.Transparent)
                                            .fillMaxWidth()
                                            .aspectRatio(1F)
                                            .clickable {
                                                if (!zoomState)
                                                    zoomInAndOut(keyColumn + keyRow * 3)
                                            }
                                        )
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

@Composable
fun MandaKeyBox(
    name: String,
    color: Color,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .border(BorderStroke(2.dp, color), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(if (isDone) color else HMColor.Background)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center,
            color = if (isDone) HMColor.Background else color,
            text = name,
            style = HmStyle.text6
        )
    }
}

@Composable
fun MandaDetailBox(
    name: String,
    darkColor: Color,
    lightColor: Color,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isDone) darkColor else lightColor)
            .clickable { onClick() }
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = name,
            color = if (isDone) HMColor.Background else HMColor.Text,
            modifier = Modifier.padding(5.dp),
            style = HmStyle.text4
        )
    }
}

@Composable
fun MandaEmptyBox(
    color: Color = HMColor.SubText,
    onClick: () -> Unit
) {
    val stroke = Stroke(
        width = 10f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(15f, 15f), phase = 0f)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1F)
            .clip(RoundedCornerShape(8.dp))
            .drawBehind {
                drawRoundRect(
                    color = color,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clickable {
                onClick()
            }
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .scale(0.35f),
            imageVector = IconPack.Plus,
            tint = HMColor.SubText,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun DetailBoxPreview() {
    MandaEmptyBox() {}
}
