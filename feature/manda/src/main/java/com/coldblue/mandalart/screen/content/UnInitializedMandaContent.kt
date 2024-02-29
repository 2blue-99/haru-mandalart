package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMChip
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.util.MandaUtils.getTagList

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun UnInitializedMandaContent(
    updateInitState: (Boolean) -> Unit,
    insertFinalManda: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var buttonClickableState by remember { mutableStateOf(false) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "당신의 최종 목표는 \n무엇인가요?",
                style = HmStyle.text24,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
        item {
            HMTextField(inputText) {
                inputText = it
                buttonClickableState = it.isNotBlank()
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
                        buttonClickableState = true
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        item {
            HMButton(text = "목표 구체화 하기", buttonClickableState) {
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