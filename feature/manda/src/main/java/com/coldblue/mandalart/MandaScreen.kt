package com.coldblue.mandalart

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMChip
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.getTagList

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
    onNextClick: () -> Unit
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
        MandaContentWithStatus(
            mandaUiState,
            mandaViewModel::updateMandaInitState,
            onNextClick
        )
    }
}

@Composable
fun MandaContentWithStatus(
    mandaUIState: MandaUIState,
    updateInitState: (Boolean) -> Unit,
    onNextClick: () -> Unit
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {}
        is MandaUIState.Error -> {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent(updateInitState, onNextClick)
        }

        is MandaUIState.InitializedSuccess -> {}
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnInitializedMandaContent(
    updateInitState: (Boolean) -> Unit,
    onNextClick: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var clickState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, top = 25.dp, end = 25.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "당신의 최종 목표는 \n무엇인가요?",
                    style = HmStyle.logo,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left
                )
            }
            item { HMTextField(inputText) {
                inputText = it
                clickState = it.isNotBlank()
            } }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    getTagList().forEach {
                        HMChip(it) {
                            inputText = it
                            clickState = true
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item { HMButton(text = "목표 구체화 하기", clickState) {
                updateInitState(true)
                onNextClick()
            }
            }
        }
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 50.dp),
//            contentAlignment = Alignment.BottomCenter,
//        ) {
//            HMButton(text = "목표 구체화 하기",clickState) { onNextClick() }
//        }
    }


}

@Composable
fun InitializedMandaContent() {

}

//@Preview
//@Composable
//fun Preview() {
//    UnInitializedMandaContent {}
//}