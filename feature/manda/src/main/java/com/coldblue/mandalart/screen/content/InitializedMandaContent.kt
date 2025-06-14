package com.coldblue.mandalart.screen.content

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.resetToBeginning
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.iconpack.Back
import com.coldblue.designsystem.iconpack.Mandalart
import com.coldblue.designsystem.iconpack.Question
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.ChangeMandaBottomSheet
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
import com.coldblue.mandalart.util.MandaUtils.checkAlertWindowPermission
import com.coldblue.mandalart.util.MandaUtils.currentColorList
import com.coldblue.mandalart.util.MandaUtils.requestPermission
import com.coldblue.model.DateRange
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTodo
import com.coldblue.todo.MandaTodoList
import com.coldblue.tutorial.TutorialScreen
import com.colddelight.mandalart.R
import com.orhanobut.logger.Logger
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
    upsertMandaTodo: (MandaTodo) -> Unit,
    getRequirePermission: () -> Boolean,
    setRequirePermission: () -> Unit,
    currentManda: Int,
    changeManda: (Int) -> Unit,
    deleteManda: (Int) -> Unit
) {
    var titleOffset by remember { mutableStateOf(Offset.Zero) }
    var mandaOffset by remember { mutableStateOf(Offset.Zero) }
    var todoOffset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var isExplain by remember { mutableStateOf(false) }
    var percentage by remember { mutableFloatStateOf(0f) }
    var isPermissionDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val animateDonePercentage = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )
    val doneComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.manda_done)
    )
    val createComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.manda_create)
    )
    val doneAni = rememberLottieAnimatable()
    val createAni = rememberLottieAnimatable()

    var showDoneAni by remember { mutableStateOf(false) }
    var showCreateAni by remember { mutableStateOf(false) }

    var currentIndex by remember { mutableIntStateOf(uiState.currentIndex) }

    var mandaChangeState by remember { mutableStateOf(false) }

    LaunchedEffect(showCreateAni) {
        if (showCreateAni) {
            createAni.animate(
                speed = 0.7f,
                composition = createComposition,
                clipSpec = LottieClipSpec.Frame(0, 1200),
                initialProgress = 0f
            ).also {
                createAni.resetToBeginning()
                showCreateAni = false
            }
        }
    }
    LaunchedEffect(showDoneAni) {
        if (showDoneAni) {
            doneAni.animate(
                speed = 0.7f,
                composition = doneComposition,
                clipSpec = LottieClipSpec.Frame(0, 1200),
                initialProgress = 0f
            ).also {
                showDoneAni = false
            }
        }
    }
    val context = LocalContext.current

    if (mandaBottomSheetUIState is MandaBottomSheetUIState.Up) {
        MandaBottomSheet(
            mandaBottomSheetContentState = mandaBottomSheetUIState.mandaBottomSheetContentState,
            sheetState = sheetState,
            mandaKeyList = uiState.mandaKeyList,
            usedColorIndexList = uiState.usedColorIndexList,
            upsertMandaFinal = {
                showCreateAni = true
                upsertMandaFinal(it)
            },
            upsertMandaKey = {
                showCreateAni = true
                upsertMandaKey(it)
            },
            upsertMandaDetail = {
                if (it.isDone) {
                    showDoneAni = true
                } else {
                    showCreateAni = true
                }
                upsertMandaDetail(it)

            },
            deleteMandaKey = deleteMandaKey,
            deleteMandaDetail = deleteMandaDetail
        ) {
            changeBottomSheet(false, null)
        }
    }
    if (mandaChangeState) {
        ChangeMandaBottomSheet(
            mandaChangeInfo = uiState.mandaChangeInfo,
            currentMandaIndex = currentManda,
            changeManda = changeManda,
            onDisMiss = { mandaChangeState = false },
            deleteManda = deleteManda,
        )
    }

    LaunchedEffect(uiState.mandaStatus.donePercentage) {
        percentage = uiState.mandaStatus.donePercentage
    }

    if (getRequirePermission()) {
        if (!checkAlertWindowPermission(context)) {
            isPermissionDialog = true
        }
    }

    if (isPermissionDialog) {
        HMTextDialog(
            topText = "원활한 알람 기능을 위해,\n",
            targetText = "다른 앱 위에 표시 권한",
            bottomText = "이 필요합니다.",
            tintColor = HMColor.Primary,
            confirmText = "지금 설정",
            onDismissRequest = {
                isPermissionDialog = false
                setRequirePermission()
            },
            onConfirm = {
                isPermissionDialog = false
                setRequirePermission()
                requestPermission(context)
            }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            MandaTopBar(
                navigateToTutorial = { isExplain = true },
                navigateToSetting = navigateToSetting,
                navigateToHistory = navigateToHistory,
                onClickDetail = { mandaChangeState = true }
            )

            Box(
                modifier = Modifier.onGloballyPositioned {
                    titleOffset = it.positionInRoot()
                    size = it.size
                }
            ) {
                MandaStatus(
                    titleName = uiState.mandaStatus.titleManda.name,
                    statusColor = uiState.mandaStatus.statusColor,
                    donePercentage = uiState.mandaStatus.donePercentage,
                    animateDonePercentage = animateDonePercentage.value,
                ) {
                    changeBottomSheet(
                        true,
                        MandaBottomSheetContentState.Insert(
                            if (currentIndex == 4 || currentIndex == -1) MandaBottomSheetContentType.MandaFinal(
                                mandaUI = uiState.mandaStatus.titleManda
                            )
                            else MandaBottomSheetContentType.MandaKey(mandaUI = uiState.mandaStatus.titleManda)
                        )
                    )
                }
            }
            Box(
                modifier = Modifier.onGloballyPositioned {
                    mandaOffset = it.positionInRoot()
                    size = it.size
                }
            ) {
                Mandalart(
                    mandaList = uiState.mandaList,
                    curIndex = uiState.currentIndex,
                    changeBottomSheet = changeBottomSheet,
                    changeCurrentIndex = {
                        changeCurrentIndex(it)
                        currentIndex = it
                    },
                    isMandaInit = !uiState.mandaChangeInfo[currentManda].isEmpty
                )
            }

            Box(
                modifier = Modifier.onGloballyPositioned {
                    todoOffset = it.positionInRoot()
                    size = it.size
                }
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

        LottieAnimation(
            composition = doneComposition,
            progress = { doneAni.progress },
            contentScale = ContentScale.FillHeight
        )

        LottieAnimation(
            composition = createComposition,
            modifier = Modifier.scale(3.0f),
            progress = { createAni.progress },
            contentScale = ContentScale.FillHeight
        )
        if (isExplain) {
            TutorialScreen(
                titleOffset = titleOffset,
                mandaOffset = mandaOffset,
                todoOffset = todoOffset,
                size = size,
                onFinished = {
                    isExplain = false
                }
            )
        }
    }
}

@Composable
fun MandaTopBar(
    navigateToTutorial: () -> Unit,
    navigateToSetting: () -> Unit,
    onClickDetail: () -> Unit,
    navigateToHistory: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = IconPack.Mandalart,
                tint = HMColor.Primary,
                contentDescription = "main_icon"
            )
            Text(
                text = "하루 만다라트",
                style = HmStyle.text18,
                modifier = Modifier.padding(horizontal = 15.dp),
                color = HMColor.Primary,
            )
        }
        Row {
            IconButton(
                onClick = { onClickDetail() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.MoreVert,
                    tint = HMColor.Primary,
                    contentDescription = "detail"
                )
            }
            IconButton(
                onClick = { navigateToTutorial() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = IconPack.Question,
                    tint = HMColor.Primary,
                    contentDescription = "question"
                )
            }
//            IconButton(
//                onClick = { navigateToHistory() }) {
//                Icon(
//                    modifier = Modifier.size(24.dp),
//                    imageVector = IconPack.History,
//                    tint = HMColor.Primary,
//                    contentDescription = "history"
//                )
//            }
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
fun MandaTopBarPreview() {
    MandaTopBar(
        navigateToTutorial = {},
        navigateToSetting = { /*TODO*/ },
        onClickDetail = {}
    ) {

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
//        Text(text = "현재 만다 $currentManda")
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

@Composable
fun Mandalart(
    mandaList: List<MandaState>,
    curIndex: Int,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState) -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    isMandaInit: Boolean
) {
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

    // 3 X 3 전체 달성 여부
    // manda key box 생성 시 색상 세팅
    // Empty 3 X 3 Box 시 null 처리
    val mandaDoneState = remember { mutableStateListOf<Color?>(null,null,null,null,null,null,null,null,null) }

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

    /**
     * 줌 상태 Back 리스너
     */
    BackHandler(isZoom) {
        zoomController(-1)
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
                                        if (!isZoom) {
                                            Spacer(modifier = Modifier
                                                .background(Color.Transparent)
                                                .fillMaxWidth()
                                                .aspectRatio(1F)
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