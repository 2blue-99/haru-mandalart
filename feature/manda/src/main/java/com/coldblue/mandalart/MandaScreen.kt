package com.coldblue.mandalart

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMChip
import com.coldblue.designsystem.component.HMEditText
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.getTagList

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel()
) {
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    val context = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    context.clearFocus()
                })
            }
    ) {
        MandaContentWithStatus(mandaUiState)
    }
}

@Composable
fun MandaContentWithStatus(
    mandaUIState: MandaUIState
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {}
        is MandaUIState.Error -> {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent()
        }

        is MandaUIState.InitializedSuccess -> {}
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnInitializedMandaContent() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                modifier = Modifier.background(Color.Red),
                text = "당신의 최종 목표는 무엇인가요?",
                style = HmStyle.logo,
                fontWeight = FontWeight.Bold
            )
        }
        item { HMEditText() }
        item {
            FlowRow(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                getTagList().forEach {
                    HMChip(it)
                }
            }
        }

    }
}

@Composable
fun InitializedMandaContent() {

}

@Preview
@Composable
fun Preview() {
    UnInitializedMandaContent()
}