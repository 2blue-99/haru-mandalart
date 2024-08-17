package com.coldblue.mandalart.screen.content

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Back
import com.coldblue.designsystem.iconpack.History
import com.coldblue.designsystem.iconpack.Mandalart
import com.coldblue.designsystem.iconpack.Question
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
import com.coldblue.mandalart.util.MandaUtils.currentColorList
import com.coldblue.model.DateRange
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTodo
import com.coldblue.todo.MandaTodoList
import com.colddelight.mandalart.R
import kotlin.math.abs
import kotlin.math.roundToInt

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
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToHistory: () -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    changeTodoRange: (DateRange) -> Unit,
    upsertMandaTodo: (MandaTodo) -> Unit
) {
    var isExplain by remember { mutableStateOf(true) }
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
            usedColorIndexList = uiState.usedColorIndexList,
            upsertMandaFinal = upsertMandaFinal,
            upsertMandaKey = upsertMandaKey,
            upsertMandaDetail = upsertMandaDetail,
            deleteMandaKey = deleteMandaKey,
            deleteMandaDetail = deleteMandaDetail
        ) {
            changeBottomSheet(false, null)
        }
    }

    LaunchedEffect(uiState.mandaStatus.donePercentage) {
        percentage = uiState.mandaStatus.donePercentage
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            ExplainBox(
                top = true
            ){
                MandaTopBar(
                    navigateToSetting = navigateToSetting,
                    navigateToHistory = navigateToHistory
                )
            }

            ExplainBox(

            ){
                MandaStatus(
                    titleName = uiState.mandaStatus.titleManda.name,
                    statusColor = uiState.mandaStatus.statusColor,
                    donePercentage = uiState.mandaStatus.donePercentage,
                    animateDonePercentage = animateDonePercentage.value,
                ) {
                    changeBottomSheet(
                        true,
                        MandaBottomSheetContentState.Insert(
                            MandaBottomSheetContentType.MandaFinal(
                                mandaUI = uiState.mandaStatus.titleManda
                            )
                        )
                    )
                }
            }

            ExplainBox(

            ) {
                Mandalart(
                    mandaList = uiState.mandaList,
                    curIndex = uiState.currentIndex,
                    changeBottomSheet = changeBottomSheet,
                    changeCurrentIndex = changeCurrentIndex
                )
            }

            ExplainBox(
                bottom = true
            ) {
                MandaTodoList(
                    colorList = currentColorList(uiState.mandaList),
                    currentIndex = uiState.currentIndex,
                    todoRange = uiState.todoRange,
                    todoList = uiState.todoList,
                    doneTodoCnt = uiState.doneTodoCnt,
                    todoCnt = uiState.todoCnt,
                    upsertMandaTodo = upsertMandaTodo,
                    changeRange = changeTodoRange,
                )
            }

        }
        if(isExplain) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HMColor.Dim)
            )
        }
    }
}

@Composable
fun ExplainBox(
    top: Boolean = false,
    bottom: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
//            .background(HMColor.Dim)
            .clip(
                if(top) RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                else if(bottom) RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                else RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp)
    ){
        content()
    }
}

@Composable
fun MandaTopBar(
    navigateToSetting: () -> Unit,
    navigateToHistory: () -> Unit
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
        Row {
            IconButton(
                //TODO
                onClick = { }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = IconPack.Question,
                    tint = HMColor.Primary,
                    contentDescription = "question"
                )
            }
            IconButton(
                onClick = { navigateToHistory() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = IconPack.History,
                    tint = HMColor.Primary,
                    contentDescription = "history"
                )
            }
            IconButton(
                onClick = { navigateToSetting() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Settings,
                    tint = HMColor.Primary,
                    contentDescription = "setting"
                )
            }
        }
    }
}

@Preview
@Composable
fun MandaTopBarPreview(){
    MandaTopBar(navigateToSetting = { /*TODO*/ }) {
        
    }
}

@Composable
fun MandaStatus(
    titleName: String,
    statusColor: Color,
    donePercentage: Float,
    animateDonePercentage: Float,
    onClickTitle: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "\"",
                style = HmStyle.text24,
                color = statusColor
            )
            ClickableText(
                modifier = Modifier.widthIn(max = (screenWidth - 60).dp),
                text = AnnotatedString(titleName.ifEmpty {
                    stringResource(id = R.string.initialized_empty_title)
                }),
                onClick = { onClickTitle() },
                style = HmStyle.text24.copy(color = statusColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "\"",
                style = HmStyle.text24,
                color = statusColor
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text(
                    text = stringResource(
                        id = R.string.initialized_done_percentage,
                        "${((donePercentage * 100).roundToInt())}%"
                    ),
                    style = HmStyle.text12,
                    color = statusColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                LinearProgressIndicator(
                    progress = { animateDonePercentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(7.dp)),
                    color = statusColor,
                    trackColor = HMColor.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Mandalart(
    mandaList: List<MandaState>,
    curIndex: Int,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit,
    changeCurrentIndex: (Int) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(curIndex) }
    LaunchedEffect(curIndex) { currentIndex = curIndex }

    var currentMandaList = remember {
        mutableStateListOf<MandaState>().apply {
            addAll(mandaList)
        }
    }
    LaunchedEffect(mandaList){
        currentMandaList.clear()
        currentMandaList.addAll(mandaList)
    }

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

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var dragOffsetX by remember { mutableStateOf(0f) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    val dampingRatio = 0.8f // 클수록 스프링 효과 감소
    val stiffness = 1600f // 클수록 빨리 확대, 축소
    val gestureAccuracy = 100f

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
                x > 0 -> {
                    offsetX += x
                    direction = MandaGestureState.Left
                }

                x < 0 -> {
                    offsetX += x
                    direction = MandaGestureState.Right
                }
            }
        } else {
            when {
                y > 0 -> {
                    offsetY += y
                    direction = MandaGestureState.Up
                }

                y < 0 -> {
                    offsetY += y
                    direction = MandaGestureState.Down
                }
            }
        }
    }

    fun gestureController() {
        isGesture = true
        scaleX = 3f
        scaleY = 3f
        when (direction) {
            MandaGestureState.Left -> {
                if (offsetX > gestureAccuracy && translateX < mandaSize.width) {
                    translateX += mandaSize.width
                    changeCurrentIndex(currentIndex - 1)
                }
            }

            MandaGestureState.Right -> {
                if (offsetX < -gestureAccuracy && translateX > -mandaSize.width) {
                    translateX -= mandaSize.width
                    changeCurrentIndex(currentIndex + 1)
                }
            }

            MandaGestureState.Up -> {
                if (offsetY > gestureAccuracy && translateY < mandaSize.height) {
                    translateY += mandaSize.height
                    changeCurrentIndex(currentIndex - 3)
                }
            }

            MandaGestureState.Down -> {
                if (offsetY < -gestureAccuracy && translateY > -mandaSize.height) {
                    translateY -= mandaSize.height
                    changeCurrentIndex(currentIndex + 3)
                }
            }

            MandaGestureState.ZoomOut -> {

            }
        }
        offsetX = 0f
        offsetY = 0f
    }

    fun zoomController(index: Int) {
        changeCurrentIndex(index)
        if (index != -1) {
            isZoom = true
            scaleX = 3f
            scaleY = 3f
            translateX += widthList[index % 3]
            translateY += heightList[index / 3]
        } else {
            isZoom = false
            scaleX = 1f
            scaleY = 1f
            translateX = 0f
            translateY = 0f
        }
    }

    fun dragController(index: Int){
        dragOffsetX = 0f
        dragOffsetY = 0f
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
            item {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = HMColor.Background
                ) {
                    Column(
                        modifier = Modifier
                            .pointerInput(isZoom) {
                                if (isZoom) {
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
                            }
                            .graphicsLayer(
                                scaleX = animatedScaleX,
                                scaleY = animatedScaleY,
                                translationX = animatedTranslateX,
                                translationY = animatedTranslateY,
                            )
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
                                        when (val bigBox =
                                            currentMandaList[keyColumn + keyRow * 3]) {
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
                                                                        val data =
                                                                            smallBox.mandaUI
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
//                                                .pointerInput(Unit) {
//                                                    detectDragGesturesAfterLongPress(
//                                                        onDrag = { change, dragAmount ->
//                                                            change.consume()
//                                                            dragOffsetX += dragAmount.x
//                                                            dragOffsetY += dragAmount.y
//                                                            com.orhanobut.logger.Logger.d("$dragOffsetX  $dragOffsetY")
//                                                        },
//                                                        onDragEnd = {
//                                                            dragController(keyColumn + keyRow * 3)
//                                                            com.orhanobut.logger.Logger.d("END")
//                                                        }
//                                                    )
//                                                }
//                                                .onGloballyPositioned {
//                                                    it.boundsInWindow().let {
//                                                        com.orhanobut.logger.Logger.d("Click")
//                                                    }
//                                                }
                                                .clip(RoundedCornerShape(8.dp))
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
                    if (isZoom) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
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
                                        zoomController(-1)
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





