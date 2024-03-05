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
import com.coldblue.mandalart.state.BottomSheetContentState
import com.coldblue.mandalart.state.BottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
) {
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    val bottomSheetUiState by mandaViewModel.bottomSheetUIState.collectAsStateWithLifecycle()
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
            bottomSheetUiState = bottomSheetUiState,
            updateInitState = mandaViewModel::updateMandaInitState,
            upsertFinalManda = mandaViewModel::upsertMandaFinal,
            upsertMandaKey = mandaViewModel::upsertMandaKey,
            upsertMandaDetail = mandaViewModel::upsertMandaDetail,
            changeBottomSheet = mandaViewModel::changeBottomSheet
        )
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    bottomSheetUiState: BottomSheetUIState,
    updateInitState: (Boolean) -> Unit,
    upsertFinalManda: (String) -> Unit,
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit,
    changeBottomSheet: (Boolean, BottomSheetContentState) -> Unit
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
                bottomSheetUIState = bottomSheetUiState,
                upsertMandaFinal = upsertFinalManda,
                upsertMandaKey = upsertMandaKey,
                upsertMandaDetail = upsertMandaDetail,
                changeBottomSheet = changeBottomSheet
            )

        }
    }
}