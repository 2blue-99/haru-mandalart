package com.coldblue.mandalart

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
import com.coldblue.mandalart.content.InitializedMandaContent
import com.coldblue.mandalart.content.UnInitializedMandaContent
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
            mandaViewModel::upsertMandaFinal
        )
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    updateInitState: (Boolean) -> Unit,
    insertFinalManda: (String) -> Unit
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {}
        is MandaUIState.Error -> {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent(
                updateInitState = updateInitState,
                insertFinalManda = insertFinalManda
            )
        }

        is MandaUIState.InitializedSuccess -> {
            InitializedMandaContent(
                uiState = mandaUIState
            )

        }
    }
}