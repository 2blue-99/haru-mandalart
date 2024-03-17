package com.coldblue.mandalart.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.model.MandaColorInfo
import com.coldblue.mandalart.model.asMandaDetail
import com.coldblue.mandalart.model.asMandaKey
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandaBottomSheet(
    mandaBottomSheetContentState: MandaBottomSheetContentState,
    sheetState: SheetState,
    upsertMandaFinal: (String) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    onDisMiss: () -> Unit
) {
    val contentType = mandaBottomSheetContentState.mandaBottomSheetContentType
    val mandaUI = contentType.mandaUI

    var inputText by remember { mutableStateOf(mandaUI.name) }
    var keyNameText by remember { mutableStateOf("") }

    var colorIndex by remember { mutableIntStateOf(MandaUtils.colorToIndex(mandaUI.darkColor)) }

    var buttonClickableState by remember { mutableStateOf(mandaUI.name.isNotBlank()) }
    var doneCheckedState by remember { mutableStateOf(mandaUI.isDone) }
    var dialogState by remember { mutableStateOf(false) }

    if (dialogState) {
        MandaKeyDialog(
            name = keyNameText,
            onDisMiss = {
                dialogState = false
                onDisMiss()
            },
            onDelete = {
                deleteMandaKey(
                    mandaUI.id,
                    (contentType as MandaBottomSheetContentType.MandaKey).groupIdList ?: emptyList()
                )
                onDisMiss()
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = sheetState,
        containerColor = HMColor.Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = contentType.title,
                style = HmStyle.text16,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp)
            ) {
                Text(
                    text = contentType.title + " 이름",
                    style = HmStyle.text16,
                    fontWeight = FontWeight.Bold
                )
                HMTextField(inputText = inputText, maxLen = contentType.maxLen) {
                    inputText = it
                    buttonClickableState = inputText.isNotBlank()
                }
            }

            when (contentType) {
                is MandaBottomSheetContentType.MandaFinal ->
                    Spacer(modifier = Modifier.height(70.dp))

                is MandaBottomSheetContentType.MandaKey ->
                    MandaBottomSheetColor(colorIndex) { colorIndex = it }

                is MandaBottomSheetContentType.MandaDetail ->
                    MandaBottomSheetDone(doneCheckedState) { doneCheckedState = it }
            }

            when (mandaBottomSheetContentState) {

                is MandaBottomSheetContentState.Insert -> {
                    HMButton(text = "저장", clickableState = buttonClickableState) {
                        when (contentType) {

                            is MandaBottomSheetContentType.MandaFinal ->
                                upsertMandaFinal(inputText)

                            is MandaBottomSheetContentType.MandaKey ->
                                upsertMandaKey(mandaUI.asMandaKey(inputText, colorIndex))

                            is MandaBottomSheetContentType.MandaDetail ->
                                upsertMandaDetail(
                                    mandaUI.asMandaDetail(
                                        inputText,
                                        doneCheckedState,
                                        colorIndex
                                    )
                                )

                        }
                        onDisMiss()
                    }
                }

                is MandaBottomSheetContentState.Update -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .height(50.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = HMColor.Gray),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                when (contentType) {
                                    is MandaBottomSheetContentType.MandaDetail -> {
                                        deleteMandaDetail(mandaUI.id)
                                        onDisMiss()
                                    }

                                    is MandaBottomSheetContentType.MandaKey -> {
                                        keyNameText = mandaUI.name
                                        dialogState = true
                                    }

                                    is MandaBottomSheetContentType.MandaFinal -> {}
                                }
                            }
                        ) {
                            Text(
                                text = "삭제",
                                style = HmStyle.text16,
                                color = HMColor.Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        HMButton(
                            text = "저장",
                            clickableState = buttonClickableState,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .weight(1f),
                        ) {
                            when (contentType) {
                                is MandaBottomSheetContentType.MandaKey ->
                                    upsertMandaKey(mandaUI.asMandaKey(inputText, colorIndex))

                                else ->
                                    upsertMandaDetail(
                                        mandaUI.asMandaDetail(
                                            inputText,
                                            doneCheckedState,
                                            colorIndex
                                        )
                                    )
                            }
                            onDisMiss()
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MandaBottomSheetColor(
    initColorIndex: Int,
    onClick: (Int) -> Unit
) {
    val colorInfoListState = remember {
        mutableStateListOf(
            MandaColorInfo(HMColor.Dark.Pink, false, 0),
            MandaColorInfo(HMColor.Dark.Red, false, 1),
            MandaColorInfo(HMColor.Dark.Orange, false, 2),
            MandaColorInfo(HMColor.Dark.Yellow, false, 3),
            MandaColorInfo(HMColor.Dark.Green, false, 4),
            MandaColorInfo(HMColor.Dark.Blue, false, 5),
            MandaColorInfo(HMColor.Dark.Indigo, false, 6),
            MandaColorInfo(HMColor.Dark.Purple, false, 7)
        )
    }
    colorInfoListState[initColorIndex].isChecked = true

    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "색상",
            modifier = Modifier.fillMaxWidth(),
            style = HmStyle.text16,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(colorInfoListState.size / 4) {column ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(4) { row ->
                        val btnIndex = row + column * 4
                        Box(
                            modifier = Modifier
                                .width((screenWidth / 7).dp)
                                .aspectRatio(1f)
                                .padding(5.dp)
                        ) {
                            RoundButton(colorInfoListState[btnIndex]) {
                                onClick(btnIndex)
                                colorInfoListState.forEachIndexed { colorIndex, colorInfo ->
                                    if (colorIndex == btnIndex)
                                        colorInfoListState[btnIndex] =
                                            colorInfoListState[btnIndex].copy(isChecked = true)
                                    else
                                        colorInfo.isChecked = false
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoundButton(
    colorInfo: MandaColorInfo,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (colorInfo.isChecked) colorInfo.color else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.fillMaxSize(fraction = 0.8f),
            onClick = {
                onClick()
            },
            shape = CircleShape,
            border = if (colorInfo.isChecked) BorderStroke(2.dp, HMColor.Background) else null,
            colors = ButtonDefaults.buttonColors(containerColor = colorInfo.color)
        ) { }
    }
}

@Composable
fun MandaKeyDialog(
    name: String,
    onDisMiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        containerColor = HMColor.Background,
        onDismissRequest = { onDisMiss() },
        text = {
            Text(
                text = "핵심목표 \"$name\" 을(를) 삭제하면 포함된 세부목표가 전부 삭제됩니다.",
                style = HmStyle.text16,
                color = HMColor.Text
            )
        },
        dismissButton = {
            TextButton(onClick = { onDisMiss() }) {
                Text(
                    text = "취소",
                    style = HmStyle.text16,
                    color = HMColor.Text,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDelete()
                onDisMiss()
            }) {
                Text(
                    text = "삭제",
                    style = HmStyle.text16,
                    color = HMColor.NegativeText,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun MandaBottomSheetDone(
    checkedState: Boolean,
    onClick: (Boolean) -> Unit
) {
    Column {
        Text(
            text = "달성 상태",
            style = HmStyle.text16,
            color = HMColor.Text,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (checkedState) "달성" else "미달성",
                style = HmStyle.text12,
                color = HMColor.Text,
            )
            HMSwitch(checked = checkedState) {
                onClick(!checkedState)
            }
        }
    }
}

@Preview
@Composable
fun BottomSheetPreview() {
    MandaBottomSheetDone(false) {}
}