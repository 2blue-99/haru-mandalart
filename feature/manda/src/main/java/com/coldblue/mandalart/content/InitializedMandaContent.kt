package com.coldblue.mandalart.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMTitleComponent
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MandaType
import com.coldblue.mandalart.state.MandaUIState
import kotlin.math.roundToInt

const val MAX_MANDA_KEY_SIZE = 8
const val MAX_MANDA_DETAIL_SIZE = 64

@Composable
fun InitializedMandaContent(
    uiState: MandaUIState.InitializedSuccess
) {
    var percentage by remember { mutableFloatStateOf(0f) }

    val animatedFloatColor = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(600, 0, LinearEasing), label = ""
    )

    LaunchedEffect(Unit) {
        percentage = uiState.donePercentage
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item { HMTitleComponent() }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                FilledIconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = HMColor.Primary)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
        item {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "\" ${uiState.finalName} \"",
                style = HmStyle.text24,
                fontWeight = FontWeight.Bold
            )

        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "핵심 목표 : ", style = HmStyle.text16)
                    Text(
                        text = "${uiState.keyMandaCnt} / $MAX_MANDA_KEY_SIZE",
                        style = HmStyle.text16
                    )
                }
                Row {
                    Text(text = "세부 목표 : ", style = HmStyle.text16)
                    Text(
                        text = "${uiState.detailMandaCnt} / $MAX_MANDA_DETAIL_SIZE",
                        style = HmStyle.text16
                    )
                }
            }
        }
        item {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "달성률 ${uiState.donePercentage.roundToInt()}", style = HmStyle.text12)
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

        item {
            MandaContent(
                mandaKeys = uiState.keys,
                mandaDetails = uiState.details
            )
        }
    }
}

@Composable
fun MandaContent(
    mandaKeys: List<MandaType>,
    mandaDetails: List<MandaType>
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Color.Companion.White)
            .fillMaxWidth()
            .aspectRatio(1F),
    ) {
        repeat(3) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(3) {
                    OutlinedButton(
                        shape = RectangleShape,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1F)
                    ) {
                    }
                }
            }
        }
    }




    for (manda in mandaKeys) {
        when (manda) {
            is MandaType.Empty -> {
                manda.manda
            }

            is MandaType.Fill -> {}
            is MandaType.Done -> {}
        }
    }

    for (manda in mandaDetails) {
        when (manda) {
            is MandaType.Empty -> {
                manda.manda
            }

            is MandaType.Fill -> {}
            is MandaType.Done -> {}
        }
    }
}

@Preview
@Composable
fun MandaContentPreview() {
//    InitializedMandaContent(
//        uiState = MandaUIState.InitializedSuccess(
//            keyMandaCnt = 10,
//            detailMandaCnt = 50,
//            donePercentage = 0.2f,
//            finalName = "TEST",
//            keys = listOf(),
//            details = listOf()
//        )
//    )
    MandaContent(
        mandaKeys = listOf(
            MandaType.Empty(MandaUI(id = 1)),
            MandaType.Empty(MandaUI(id = 2)),
            MandaType.Empty(MandaUI(id = 3)),
            MandaType.Empty(MandaUI(id = 4)),
            MandaType.Empty(MandaUI(id = 5)),
            MandaType.Empty(MandaUI(id = 6)),
            MandaType.Empty(MandaUI(id = 7)),
            MandaType.Empty(MandaUI(id = 8)),
        ),
        mandaDetails = listOf()
    )
}