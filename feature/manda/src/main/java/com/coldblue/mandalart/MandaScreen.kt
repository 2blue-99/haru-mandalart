package com.coldblue.mandalart

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.coldblue.designsystem.component.HMTitleComponent
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.util.getTagList
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import kotlinx.coroutines.delay

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
        MandaContentWithStatus(
            mandaUiState,
            mandaViewModel::updateMandaInitState,
            mandaViewModel::upsertMandaKey
        )
    }
}

@Composable
fun MandaContentWithStatus(
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
                uiData = mandaUIState
            )

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnInitializedMandaContent(
    updateInitState: (Boolean) -> Unit,
    insertFinalManda: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var clickState by remember { mutableStateOf(false) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 25.dp, end = 25.dp)
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
        item {
            HMTextField(inputText) {
                inputText = it
                clickState = it.isNotBlank()
            }
        }
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

        item {
            HMButton(text = "목표 구체화 하기", clickState) {
                updateInitState(true)
                insertFinalManda(inputText)
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

@Composable
fun InitializedMandaContent(
    uiData: MandaUIState.InitializedSuccess
) {
    var percentage by remember { mutableFloatStateOf(0f) }

    val animatedFloatColor = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )

    LaunchedEffect(Unit) {
        percentage = uiData.donePercentage
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, top = 25.dp),
    ) {
        item { HMTitleComponent() }
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\" 내가 한다 취업 \"",
                    style = HmStyle.headline,
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "핵심 목표 : ", style = HmStyle.title)
                    Text(text = "${uiData.keyMandaCnt} / 8", style = HmStyle.title)
                }
                Row {
                    Text(text = "핵심 목표 : ", style = HmStyle.title)
                    Text(text = "${uiData.detailMandaCnt} / 64", style = HmStyle.title)
                }
            }
        }
        item {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "달성률 $0", style = HmStyle.content)
            }
            Spacer(modifier = Modifier.height(5.dp))

            LinearProgressIndicator(
                progress = { animatedFloatColor.value },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = HMColor.Primary,
                trackColor = HMColor.Gray
            )
        }
    }
}

@Preview
@Composable
fun MandaContentPreview() {
    InitializedMandaContent(
        uiData = MandaUIState.InitializedSuccess(
            keyMandaCnt = 10,
            detailMandaCnt = 50,
            donePercentage = 0.2f,
            keys = listOf(MandaKey("", 1, 0)),
            details = listOf(MandaDetail(1, "1", true, 0))
        )
    )
}