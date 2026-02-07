package com.coldblue.mandalart.screen.content.madalart

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.MandaDetailBox
import com.coldblue.mandalart.screen.MandaEmptyBox
import com.coldblue.mandalart.screen.MandaKeyBox
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaGestureState
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.orhanobut.logger.Logger
import kotlin.math.abs

@Composable
fun Mandalart(
    mandaList: List<MandaState>,
    curIndex: Int,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    isMandaInit: Boolean
) {
    val controller = remember { MandalartGestureController() }

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

    val dampingRatio = 0.8f // 클수록 스프링 효과 감소
    val stiffness = 1600f // 클수록 빨리 확대, 축소

    val animatedScaleX by animateFloatAsState(
        targetValue = controller.scaleX,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )
    val animatedScaleY by animateFloatAsState(
        targetValue = controller.scaleY,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ), label = ""
    )
    val animatedTranslateX by animateFloatAsState(
        targetValue = controller.translateX,
        label = "",
        finishedListener = {
            controller.isGesture = false
        }
    )
    val animatedTranslateY by animateFloatAsState(
        targetValue = controller.translateY,
        label = "",
        finishedListener = {
            controller.isGesture = false
        }
    )

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
                                scaleX = animatedScaleX,
                                scaleY = animatedScaleY,
                                translationX = animatedTranslateX,
                                translationY = animatedTranslateY,
                            )
                            .onGloballyPositioned {
                                controller.mandaSize = it.size.toSize()
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
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 5.dp)
                                            .clip(RoundedCornerShape(8))
                                            .background(mandaDoneState[bigBox.id-1] ?: HMColor.Background)
                                    ) {
                                        when (bigBox) {
                                            is MandaState.Empty -> {
                                                mandaDoneState[bigBox.id - 1] = null
                                                MandaEmptyBox(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    if (isMandaInit) {
                                                        changeBottomSheet(
                                                            true,
                                                            MandaBottomSheetContentState.Insert(
                                                                MandaBottomSheetContentType.MandaKey(
                                                                    MandaUI(id = bigBox.id),
                                                                    null
                                                                )
                                                            )
                                                        )
                                                    } else {
                                                        mandaDialogState = true
                                                    }

                                                }
                                            }

                                            is MandaState.Exist -> {
                                                Column(
                                                    horizontalAlignment = Alignment.Start,
                                                    verticalArrangement = Arrangement.Top,
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    // 1 X 3 만다라트
                                                    repeat(3) { detailRow ->
                                                        Row {
                                                            // 1 X 1 만다라트
                                                            repeat(3) { detailColumn ->
                                                                when (val smallBox = bigBox.mandaUIList[detailColumn + detailRow * 3]) {
                                                                    is MandaType.None -> {
                                                                        MandaEmptyBox(
                                                                            modifier = Modifier.weight(
                                                                                1f
                                                                            )
                                                                        ) {
                                                                            if (isMandaInit) {
                                                                                changeBottomSheet(
                                                                                    true,
                                                                                    MandaBottomSheetContentState.Insert(
                                                                                        if (bigBox.id == 5) {
                                                                                            if (smallBox.mandaUI.id == 5) {
                                                                                                MandaBottomSheetContentType.MandaFinal(
                                                                                                    smallBox.mandaUI
                                                                                                )
                                                                                            } else {
                                                                                                MandaBottomSheetContentType.MandaKey(
                                                                                                    smallBox.mandaUI,
                                                                                                )
                                                                                            }

                                                                                        } else {
                                                                                            MandaBottomSheetContentType.MandaDetail(
                                                                                                smallBox.mandaUI,
                                                                                            )
                                                                                        }
                                                                                    )
                                                                                )
                                                                            } else {
                                                                                if (bigBox.id == 5 && smallBox.mandaUI.id == 5) {
                                                                                    changeBottomSheet(
                                                                                        true,
                                                                                        MandaBottomSheetContentState.Insert(
                                                                                            MandaBottomSheetContentType.MandaFinal(
                                                                                                smallBox.mandaUI
                                                                                            )
                                                                                        )
                                                                                    )

                                                                                } else {
                                                                                    mandaDialogState =
                                                                                        true
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    is MandaType.Key -> {
                                                                        val smallBoxData = smallBox.mandaUI
                                                                        mandaDoneState[smallBoxData.id-1] = if(smallBoxData.isDone) smallBoxData.color else null
                                                                        MandaKeyBox(
                                                                            modifier = Modifier.weight(1f),
                                                                            name = smallBoxData.name,
                                                                            backgroundColor = smallBoxData.color,
                                                                            borderColor = smallBoxData.color,
                                                                            isDone = if(smallBoxData.id == 5 ) true else smallBoxData.isDone,
                                                                            isCenter = keyColumn == 1 && keyRow == 1
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                if (bigBox.id == 5 && smallBoxData.id == 5) {
                                                                                    MandaBottomSheetContentState.Insert(
                                                                                        MandaBottomSheetContentType.MandaFinal(
                                                                                            smallBoxData
                                                                                        )
                                                                                    )
                                                                                } else {
                                                                                    MandaBottomSheetContentState.Update(
                                                                                        MandaBottomSheetContentType.MandaKey(
                                                                                            mandaUI = smallBoxData,
                                                                                            groupIdList = smallBox.groupIdList
                                                                                        )
                                                                                    )
                                                                                }
                                                                            )
                                                                        }
                                                                    }

                                                                    is MandaType.Detail -> {
                                                                        val data = smallBox.mandaUI
                                                                        // 전체 달성여부 체크하여 BorderColor 변경
                                                                        var isAllDone = false
                                                                        if(bigBox.mandaUIList.size == 9) isAllDone = bigBox.mandaUIList[4].mandaUI.isDone
                                                                        MandaDetailBox(
                                                                            modifier = Modifier.weight(1f),
                                                                            name = data.name,
                                                                            backgroundColor = data.color,
                                                                            borderColor = if(isAllDone) HMColor.Background else data.color,
                                                                            isDone = data.isDone
                                                                        ) {
                                                                            changeBottomSheet(
                                                                                true,
                                                                                MandaBottomSheetContentState.Update(
                                                                                    MandaBottomSheetContentType.MandaDetail(
                                                                                        mandaUI = data
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
                                        if (!controller.isZoom) {
                                            Spacer(modifier = Modifier
                                                .background(Color.Transparent)
                                                .fillMaxWidth()
                                                .aspectRatio(1F)
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable {
                                                    if (!controller.isZoom) {
                                                        Logger.d("호출전 $currentIndex")
                                                        controller.zoom(keyColumn + keyRow * 3)
                                                        changeCurrentIndex(keyColumn + keyRow * 3)
                                                        Logger.d("호출후 $currentIndex")


                                                    }
                                                }
                                            )
                                        }
                                    }
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