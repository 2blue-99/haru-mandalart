package com.coldblue.mandalart.screen.content.madalart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.iconpack.Back
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.screen.content.rememberMandalartTransform
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaState

@Composable
fun Mandalart(
    mandaList: List<MandaState>,
    curIndex: Int,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    isMandaInit: Boolean
) {
    val controller = remember { MandalartGestureController() }
    val transform = rememberMandalartTransform(controller)

    var currentIndex by remember { mutableIntStateOf(curIndex) }
    var mandaDialogState by remember { mutableStateOf(false) }
    LaunchedEffect(curIndex) { currentIndex = curIndex }
    var currentMandaList = remember {
        mutableStateListOf<MandaState>().apply {
            addAll(mandaList)
        }
    }
    LaunchedEffect(mandaList) {
        currentMandaList.clear()
        currentMandaList.addAll(mandaList)
    }

    // 3 X 3 전체 달성 여부
    // manda key box 생성 시 색상 세팅
    // Empty 3 X 3 Box 시 null 처리
    val mandaDoneState = remember { mutableStateListOf<Color?>(null,null,null,null,null,null,null,null,null) }

    /**
     * 줌 상태 Back 리스너
     */
    BackHandler(controller.isZoom) {
        controller.reset()
    }

    if (mandaDialogState) {
        HMTextDialog(
            targetText = "",
            bottomText = "중앙 목표를 먼저 입력해 주세요.",
            confirmText = "확인",
            onDismissRequest = {
                mandaDialogState = false
            },
            tintColor = HMColor.Primary,
            onConfirm = {
                mandaDialogState = false
            },
            canCancel = false
        )
    }

    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(1.5.dp, HMColor.DarkGray, shape = RoundedCornerShape(8.dp))
        ) {
            // 81(세로) X 81 만다라트
            item {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = HMColor.Background
                ) {
                    Column(
                        modifier = Modifier
                            .pointerInput(controller.isZoom) {
                                if (controller.isZoom) {
                                    detectDragGestures(
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            controller.dragStartDetector(dragAmount)
                                        },
                                        onDragEnd = {
                                            controller.onDragEnd(currentIndex,changeCurrentIndex)
                                            controller.onDragEnd(currentIndex,changeCurrentIndex)
                                        }
                                    )
                                }
                            }
                            .graphicsLayer(
                                scaleX = transform.scaleX,
                                scaleY = transform.scaleY,
                                translationX = transform.translateX,
                                translationY = transform.translateY,
                            )
                            .onGloballyPositioned {
                                if (controller.mandaSize == Size.Zero){
                                    val mandaSize = it.size.toSize()
                                    controller.initMandaSize(mandaSize,mandaSize.width,mandaSize.height)
                                }
                            }
                    ) {
                        // 3 X 9 만다라트
                        repeat(3) { keyRow ->
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            ) {
                                // 3 X 3 만다라트
                                repeat(3) { keyColumn ->
                                    val bigBox = currentMandaList[keyColumn + keyRow * 3]

                                    MandalartBigBox( modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 5.dp)
                                        .clip(RoundedCornerShape(8))
                                        .background(mandaDoneState[bigBox.id-1] ?: HMColor.Background),
                                        mandaState = bigBox,
                                        isZoom = controller.isZoom,
                                        isMandaInit = isMandaInit,
                                        onZoom = {
                                            changeCurrentIndex(keyColumn + keyRow * 3)
                                            controller.zoom(keyColumn + keyRow * 3)
                                        },
                                        onInsert = changeBottomSheet,
                                        onDialog = {mandaDialogState = true},
                                        onInit = {mandaDoneState[bigBox.id - 1] = null},
                                        isMandaCenter = keyColumn == 1 && keyRow == 1,
                                        setMandaDoneState = {id,isDonne,color->
                                            mandaDoneState[id] = if(isDonne) color else null
                                        }
                                        )
                                }
                            }
                        }
                    }
                    if (controller.isZoom) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(topStart = 18f)),
                            contentAlignment = Alignment.BottomEnd,
                        ) {
                            Surface(
                                color = HMColor.Background,
                                modifier = Modifier
                                    .border(
                                        2.dp,
                                        HMColor.DarkGray,
                                        RoundedCornerShape(topStart = 18f, bottomEnd = 18f)
                                    )
                                    .clickable(true) {
                                        controller.reset()
                                    }
                            ) {
                                Icon(
                                    imageVector = IconPack.Back,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(10.dp),
                                    tint = HMColor.Primary,
                                    contentDescription = "Zoom"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}