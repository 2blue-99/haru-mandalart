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
import com.coldblue.mandalart.state.MandaUIState

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
) {
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
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
            mandaUiState,
            mandaViewModel::updateMandaInitState,
            mandaViewModel::upsertMandaFinal,
            mandaViewModel::upsertMandaKey,
            mandaViewModel::upsertMandaDetail,
        )
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    updateInitState: (Boolean) -> Unit,
    upsertFinalManda: (String) -> Unit,
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit
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
                upsertMandaKey = upsertMandaKey,
                upsertMandaDetail = upsertMandaDetail
            )

        }
    }
}