package com.coldblue.mandalart.screen.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Mandalart
import com.coldblue.designsystem.iconpack.ZoomIn
import com.coldblue.designsystem.iconpack.ZoomOut
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.MandaBottomSheet
import com.coldblue.mandalart.screen.MandaDetailBox
import com.coldblue.mandalart.screen.MandaEmptyBox
import com.coldblue.mandalart.screen.MandaKeyBox
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaGestureState
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R
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
    upsertMandaFinal: (MandaKey) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    deleteMandaAll: () -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit,
    navigateToSetting: () -> Unit,
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
            mandaKeyList = uiState.mandaKeyList,
            upsertMandaFinal = upsertMandaFinal,
            upsertMandaKey = upsertMandaKey,
            upsertMandaDetail = upsertMandaDetail,
            deleteMandaKey = deleteMandaKey,
            deleteMandaDetail = deleteMandaDetail
        ) {
            changeBottomSheet(false, null)
        }
    }

    LaunchedEffect(uiState.donePercentage) { percentage = uiState.donePercentage }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        MandaTitle{
            navigateToSetting()
        }

        MandaStatus(
            finalName = uiState.finalManda.name,
            donePercentage = uiState.donePercentage,
            animateDonePercentage = animateDonePercentage.value
        ) {
            changeBottomSheet(
                true,
                MandaBottomSheetContentState.Insert(
                    MandaBottomSheetContentType.MandaFinal(
                        mandaUI = uiState.finalManda
                    )
                )
            )
        }

        Mandalart(
            mandaStateList = uiState.mandaStateList,
            changeBottomSheet = changeBottomSheet
        )
    }
}

@Composable
fun MandaTitle(
    navigateToSetting: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = IconPack.Mandalart,
                tint = HMColor.Primary,
                contentDescription = "main_icon"
            )
            Text(
                text = "하루 만다라트",
                style = HmStyle.text16,
                modifier = Modifier.padding(horizontal = 15.dp),
                color = HMColor.Primary,
            )
        }
        IconButton(
            onClick = { navigateToSetting() }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Settings,
                tint = HMColor.Primary,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun MandaStatus(
    finalName: String,
    donePercentage: Float,
    animateDonePercentage: Float,
    onClickTitle: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ClickableText(
            text = AnnotatedString("\" $finalName \""),
            onClick = { onClickTitle() },
            style = HmStyle.text24.copy(color = HMColor.Primary),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text(
                    text = stringResource(
                        id = R.string.initialized_done_percentage,
                        ((donePercentage * 100).roundToInt())
                    ),
                    style = HmStyle.text12,
                    color = HMColor.Primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                LinearProgressIndicator(
                    progress = { animateDonePercentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(7.dp)),
                    color = HMColor.Primary,
                    trackColor = HMColor.Gray
                )
            }
        }
    }
}

@Composable
fun Mandalart(
    mandaStateList: List<MandaState>,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit
) {
    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var translateX by remember { mutableFloatStateOf(0f) }
    var translateY by remember { mutableFloatStateOf(0f) }
    var isZoom by remember { mutableStateOf(false) }
    var isGesture by remember { mutableStateOf(false) }
    var direction by remember { mutableStateOf(MandaGestureState.Down) }

    var mandaSize by remember { mutableStateOf(Size.Zero) }

    val widthList = listOf(mandaSize.width, 0f, -mandaSize.width)
    val heightList = listOf(mandaSize.height, 0f, -mandaSize.height)

    val dampingRatio = 0.8f // 클수록 스프링 효과 감소
    val stiffness = 1200f // 클수록 빨리 확대, 축소

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
    val animatedTranslateX by animateFloatAsState(
        targetValue = translateX,
        label = "",
        finishedListener = {
            isGesture = false
        }
    )
    val animatedTranslateY by animateFloatAsState(
        targetValue = translateY,
        label = "",
        finishedListener = {
            isGesture = false
        }
    )

    fun dragStartDetector(dragAmount: Offset) {
        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            when {
                x > 0 -> direction = MandaGestureState.Left
                x < 0 -> direction = MandaGestureState.Right
            }
        } else {
            when {
                y > 0 -> direction = MandaGestureState.Up
                y < 0 -> direction = MandaGestureState.Down
            }
        }
    }

    fun gestureController() {
        isGesture = true
        scaleX = 3f
        scaleY = 3f
        when (direction) {
            MandaGestureState.Left -> {
                if(translateX < mandaSize.width)
                    translateX += mandaSize.width
            }

            MandaGestureState.Right -> {
                if(translateX > -mandaSize.width)
                    translateX -= mandaSize.width
            }

            MandaGestureState.Up -> {
                if(translateY < mandaSize.height)
                    translateY += mandaSize.height
            }

            MandaGestureState.Down -> {
                if(translateY > -mandaSize.height)
                    translateY -= mandaSize.height
            }
        }
    }

    fun zoomController(index: Int) {
        Logger.d(index)
        if(index != -1){
            isZoom = true
            scaleX = 3f
            scaleY = 3f
            translateX += widthList[index % 3]
            translateY += heightList[index / 3]
        }else{
            isZoom = false
            scaleX = 1f
            scaleY = 1f
            translateX = 0f
            translateY = 0f
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = if (isZoom) HMColor.Background else HMColor.Primary,
                modifier = Modifier.border(
                    if (isZoom) 2.5.dp else 0.dp,
                    HMColor.Primary,
                    CircleShape
                ),
                onClick = {
                    if (isZoom)
                        zoomController(-1)
                    else
                        zoomController(1)
                }) {
                if (isZoom)
                    Icon(
                        imageVector = IconPack.ZoomOut,
                        tint = HMColor.Primary,
                        contentDescription = ""
                    )
                else
                    Icon(
                        imageVector = IconPack.ZoomIn,
                        tint = HMColor.Background,
                        contentDescription = ""
                    )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(1.5.dp, HMColor.DarkGray, shape = RoundedCornerShape(8.dp))
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = animatedScaleX,
                                scaleY = animatedScaleY,
                                translationX = animatedTranslateX,
                                translationY = animatedTranslateY,
                            )
                            .pointerInput(isZoom) {
                                if (isZoom)
                                    detectDragGestures(
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            dragStartDetector(dragAmount)
                                        },
                                        onDragEnd = {
                                            gestureController()
                                        }
                                    )
                            }
                            .onGloballyPositioned {
                                mandaSize = it.size.toSize()
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
                                                                            MandaEmptyBox {
                                                                                changeBottomSheet(
                                                                                    true,
                                                                                    MandaBottomSheetContentState.Insert(
                                                                                        if (bigBox.id == 5) {
                                                                                            MandaBottomSheetContentType.MandaKey(
                                                                                                smallBox.mandaUI,
                                                                                            )
                                                                                        } else {
                                                                                            MandaBottomSheetContentType.MandaDetail(
                                                                                                smallBox.mandaUI,
                                                                                            )
                                                                                        }
                                                                                    )
                                                                                )
                                                                            }
                                                                        }
                                                                    }

                                                                    is MandaType.Key -> {
                                                                        val smallBoxData =
                                                                            smallBox.mandaUI
                                                                        Box(
                                                                            modifier = Modifier
                                                                                .weight(1f)
                                                                                .padding(2.dp)
                                                                        ) {
                                                                            MandaKeyBox(
                                                                                name = smallBoxData.name,
                                                                                color = smallBoxData.color,
                                                                                isDone = smallBoxData.isDone
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
                                                                                                smallBoxData,
                                                                                                smallBox.groupIdList
                                                                                            )
                                                                                        )
                                                                                    }
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
                                                                                color = data.color,
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
                                        if (!isZoom) {
                                            Spacer(modifier = Modifier
                                                .background(Color.Transparent)
                                                .fillMaxWidth()
                                                .aspectRatio(1F)
                                                .clickable {
                                                    if (!isZoom) {
                                                        zoomController(keyColumn + keyRow * 3)
                                                    }
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
}





