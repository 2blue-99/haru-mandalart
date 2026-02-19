package com.coldblue.mandalart.screen.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.screen.ChangeMandaBottomSheet
import com.coldblue.mandalart.screen.MandaBottomSheet
import com.coldblue.mandalart.screen.content.madalart.Mandalart
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaBottomSheetUIState
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

    var showDoneAni by remember { mutableStateOf(false) }
    var showCreateAni by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(uiState.currentIndex) }

    var mandaChangeState by remember { mutableStateOf(false) }


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
        MandaAnimation(
            showDone = showDoneAni,
            showCreate = showCreateAni,
            onDoneFinished = { showDoneAni = false },
            onCreateFinished = { showCreateAni = false }
        )
    }
}

@Preview
@Composable
fun MandaTopBarPreview() {
    MandaTopBar(
        navigateToTutorial = {},
        navigateToSetting = { /*TODO*/ },
        onClickDetail = {}
    )
}