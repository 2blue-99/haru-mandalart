package com.coldblue.mandalart.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.mandalart.MandaViewModel
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.content.InitializedMandaContent
import com.coldblue.mandalart.screen.content.UnInitializedMandaContent
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
) {
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    val bottomSheetUiState by mandaViewModel.mandaBottomSheetUIState.collectAsStateWithLifecycle()
    val context = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    context.clearFocus()
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
            changeBottomSheet = mandaViewModel::changeBottomSheet
        )
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    mandaBottomSheetUiState: MandaBottomSheetUIState,
    updateInitState: (Boolean) -> Unit,
    upsertFinalManda: (String) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    deleteMandaAll: () -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {}
        is MandaUIState.Error -> {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent(
                updateInitState = updateInitState,
                insertFinalManda = upsertFinalManda
            )
        }

        is MandaUIState.InitializedSuccess -> {
            InitializedMandaContent(
                uiState = mandaUIState,
                mandaBottomSheetUIState = mandaBottomSheetUiState,
                upsertMandaFinal = upsertFinalManda,
                upsertMandaKey = upsertMandaKey,
                upsertMandaDetail = upsertMandaDetail,
                deleteMandaKey = deleteMandaKey,
                deleteMandaDetail = deleteMandaDetail,
                deleteMandaAll = deleteMandaAll,
                changeBottomSheet = changeBottomSheet
            )

        }
    }
}