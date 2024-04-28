package com.coldblue.mandalart.screen.content

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMChip
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.mandaFinalMaxLen
import com.coldblue.mandalart.util.MandaUtils.getTagList
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun UnInitializedMandaContent(
    updateInitState: (Boolean) -> Unit,
    insertFinalManda: (MandaKey) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var buttonClickableState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.unInitialized_title),
            style = HmStyle.text24,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        HMTextField(inputText, maxLen = mandaFinalMaxLen) {
            inputText = it
            buttonClickableState = it.isNotBlank()
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
        ) {
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
        }
        HMButton(
            text = stringResource(id = R.string.unInitialized_goal_realization),
            modifier = Modifier.weight(0.1f),
            clickableState = buttonClickableState
        ) {
            updateInitState(true)
            insertFinalManda(MandaKey(id = 5, name = inputText))
        }
    }
}

@Preview
@Composable
fun PreviewUnInit() {
    UnInitializedMandaContent(
        updateInitState = {},
        insertFinalManda = {}
    )
}