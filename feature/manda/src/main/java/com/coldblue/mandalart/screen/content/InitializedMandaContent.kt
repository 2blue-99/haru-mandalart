package com.coldblue.mandalart.screen.content

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMMandaEmptyButton
import com.coldblue.designsystem.component.HMMandaFillButton
import com.coldblue.designsystem.component.HMMandaOutlineButton
import com.coldblue.designsystem.component.HMTitleComponent
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.state.MandaUIState
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
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit,
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
            upsertMandaKey = upsertMandaKey,
            upsertMandaDetail = upsertMandaDetail
        ) { changeBottomSheet(false, null) }
    }

    LaunchedEffect(Unit) { percentage = uiState.donePercentage }

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
            upsertMandaKey = upsertMandaKey,
            upsertMandaDetail = upsertMandaDetail,
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
        modifier = Modifier.fillMaxWidth()
    ) {
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickTitle(MandaUI(id = 4, name = finalName)) },
            textAlign = TextAlign.Center,
            text = "\" $finalName \"",
            style = HmStyle.text24,
            fontWeight = FontWeight.Bold,
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
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit
) {
    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var pivotX by remember { mutableFloatStateOf(0f) }
    var pivotY by remember { mutableFloatStateOf(0f) }
    var zoomState by remember { mutableStateOf(false) }
    var beforeDirection by remember { mutableIntStateOf(-1) }
    var afterDirection by remember { mutableIntStateOf(-1) }

    val dampingRatio = 1f // 클수록 스프링 효과 작음
    val stiffness = 1000f // 클수록 빨리 확대, 축소 됨

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

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                modifier = Modifier.size(30.dp),
                onClick = {
                    if (zoomState)
                        zoomInAndOut(-1)
                    else
                        zoomInAndOut(1) // TODO 임의 값
                }
            ) { Text(text = "C") }
        }

        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
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
                                    when (val state = mandaStateList[keyColumn + keyRow * 3]) {
                                        is MandaState.Empty -> {
                                            Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                HMMandaEmptyButton {
//                                                    upsertMandaKey(MandaUI(id = state.id))
                                                    changeBottomSheet(
                                                        true,
                                                        MandaBottomSheetContentState.Insert(
                                                            MandaBottomSheetContentType.MandaKey(
                                                                MandaUI(id = state.id)
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
                                                    Row(modifier = Modifier.fillMaxWidth()) {
                                                        repeat(3) { detailColumn ->
                                                            when (val type =
                                                                state.mandaUIList[detailColumn + detailRow * 3]) {
                                                                is MandaType.None -> {
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        HMMandaEmptyButton {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Insert(
                                                                                    MandaBottomSheetContentType.MandaKey(
                                                                                        type.mandaUI
                                                                                    )
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }

                                                                is MandaType.KeyStart ->
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        HMMandaOutlineButton(
                                                                            name = type.mandaUI.name,
                                                                            outlineColor = type.mandaUI.darkColor
                                                                        ) {
                                                                            if (type.mandaUI.id == 4) {
                                                                                changeBottomSheet(
                                                                                    true,
                                                                                    MandaBottomSheetContentState.Update(
                                                                                        MandaBottomSheetContentType.MandaFinal(
                                                                                            type.mandaUI
                                                                                        )
                                                                                    )
                                                                                )
                                                                            } else {
                                                                                changeBottomSheet(
                                                                                    true,
                                                                                    MandaBottomSheetContentState.Update(
                                                                                        MandaBottomSheetContentType.MandaKey(
                                                                                            type.mandaUI
                                                                                        )
                                                                                    )
                                                                                )
                                                                            }
                                                                        }
                                                                    }

                                                                is MandaType.DetailStart ->
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        HMMandaFillButton(
                                                                            name = type.mandaUI.name,
                                                                            backgroundColor = type.mandaUI.lightColor,
                                                                            textColor = HMColor.Text
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Update(
                                                                                    MandaBottomSheetContentType.MandaDetail(
                                                                                        type.mandaUI
                                                                                    )
                                                                                )
                                                                            )
                                                                        }
                                                                    }

                                                                is MandaType.Done ->
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .weight(1f)
                                                                            .padding(2.dp)
                                                                    ) {
                                                                        HMMandaFillButton(
                                                                            name = type.mandaUI.name,
                                                                            backgroundColor = type.mandaUI.darkColor,
                                                                            textColor = HMColor.Background
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Update(
                                                                                    MandaBottomSheetContentType.MandaDetail(
                                                                                        type.mandaUI
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

//@Preview
//@Composable
//fun MandaContentPreview() {
//    val stateList = mutableListOf<MandaState>()
//    val typeList = mutableListOf<MandaType>()
//    repeat(9) { cnt ->
//        if (cnt == 8) {
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//            typeList.add(MandaType.DetailStart(MandaUI(name = "Test", id = 1)))
//        } else {
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//            typeList.add(MandaType.None(MandaUI(id = 0)))
//        }
//        stateList.add(
//            MandaState.Exist(
//                mandaUIList = typeList.toList()
//            )
//        )
//        typeList.clear()
//    }
//}

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