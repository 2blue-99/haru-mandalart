package com.coldblue.mandalart.screen

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.MandaViewModel
import com.coldblue.mandalart.UpdateNoteViewModel
import com.coldblue.mandalart.screen.content.InitializedMandaContent
import com.coldblue.mandalart.screen.content.UnInitializedMandaContent
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.state.MandaUpdateDialogState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.orhanobut.logger.Logger


@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
    updateNoteViewModel: UpdateNoteViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
) {
    val mandaUpdateUiState by updateNoteViewModel.mandaUpdateDialogUIState.collectAsStateWithLifecycle()
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    val bottomSheetUiState by mandaViewModel.mandaBottomSheetUIState.collectAsStateWithLifecycle()
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    when (val uiState = mandaUpdateUiState) {
        is MandaUpdateDialogState.Show -> {
            UpdateDialog(
                updateNote = uiState.updateNote,
                onUpdate = updateNoteViewModel::showPlayStore,
                onDismiss = { updateNoteViewModel.changeUpdateNoteDialog(true, null) }
            )
        }

        else -> { /*TODO  인터넷 연결 X */
        }
    }

    LaunchedEffect(Unit) {
        checkUpdate(context) { updateNoteViewModel.getUpdateNote() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focus.clearFocus()
                })
            }
    ) {
        MandaContentWithState(
            mandaUIState = mandaUiState,
            mandaBottomSheetUiState = bottomSheetUiState,
            updateInitState = mandaViewModel::updateMandaInitState,
            upsertFinalManda = mandaViewModel::upsertMandaFinal,
            upsertMandaKey = mandaViewModel::upsertMandaKey,
            upsertMandaDetail = mandaViewModel::upsertMandaDetail,
            deleteMandaKey = mandaViewModel::deleteMandaKey,
            deleteMandaDetail = mandaViewModel::deleteMandaDetail,
            deleteMandaAll = mandaViewModel::deleteMandaAll,
            changeBottomSheet = mandaViewModel::changeBottomSheet,
            navigateToSetting = navigateToSetting,
            changeCurrentIndex = mandaViewModel::changeCurrentIndex,
            changeTodoRange = mandaViewModel::changeTodoRange
        )
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    mandaBottomSheetUiState: MandaBottomSheetUIState,
    updateInitState: (Boolean) -> Unit,
    upsertFinalManda: (MandaKey) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    deleteMandaAll: () -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit,
    navigateToSetting: () -> Unit,
    changeCurrentIndex: (Int) -> Unit,
    changeTodoRange: (Int) -> Unit,
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {
            Text(text = "로딩", style = HmStyle.text24)
        }
        is MandaUIState.Error -> {
            Text(text = "에러", style = HmStyle.text24)

        }
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent(
                updateInitState = updateInitState,
                insertFinalManda = upsertFinalManda
            )
        }

        is MandaUIState.InitializedSuccess -> {
            Text(text = "현재 위치${mandaUIState.todoList}")
            InitializedMandaContent(
                uiState = mandaUIState,
                mandaBottomSheetUIState = mandaBottomSheetUiState,
                upsertMandaFinal = upsertFinalManda,
                upsertMandaKey = upsertMandaKey,
                upsertMandaDetail = upsertMandaDetail,
                deleteMandaKey = deleteMandaKey,
                deleteMandaDetail = deleteMandaDetail,
                deleteMandaAll = deleteMandaAll,
                changeBottomSheet = changeBottomSheet,
                navigateToSetting = navigateToSetting,
                changeCurrentIndex = changeCurrentIndex,
                changeTodoRange = changeTodoRange,
            )

        }
    }
}

private fun checkUpdate(
    context: Context,
    onUpdate: () -> Unit
) {
    val appUpdateManager = AppUpdateManagerFactory.create(context)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        // 업데이트 할게 있는지 체크
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            // 몇번 물어봤는지 체크 + 업데이트 불가능하면 NULL
            && (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= 7
            // 즉시, 유연한 업데이트 가능한지 체크
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
        ) {
            onUpdate()
        }
    }
}


