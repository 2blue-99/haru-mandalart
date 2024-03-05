package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.model.MandaColorInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandaBottomSheet(
    mandaBottomSheetContentState: MandaBottomSheetContentState,
    sheetState: SheetState,
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit,
    onDisMiss: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var colorIndex by remember { mutableIntStateOf(0) }
    var buttonClickableState by remember { mutableStateOf(false) }
    var doneCheckedState by remember { mutableStateOf(false) }

    val contentType = mandaBottomSheetContentState.mandaBottomSheetContentType

    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Text(
                text = contentType.title,
                style = HmStyle.text16,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp)
            ) {
                Text(text = contentType.title + " 이름", style = HmStyle.text16)
                HMTextField {
                    inputText = it
                    if (inputText.isBlank()) buttonClickableState = false
                }
            }

            if (contentType is MandaBottomSheetContentType.MandaKey) {
                MandaBottomSheetColor {
                    colorIndex = it
                }
            }

            if (contentType is MandaBottomSheetContentType.MandaDetail) {
                MandaBottomSheetDone(doneCheckedState) {
                    doneCheckedState = it
                }
            }

            if (mandaBottomSheetContentState is MandaBottomSheetContentState.Insert) {
                HMButton(text = "저장", clickableState = buttonClickableState) {
                    when (contentType) {
                        is MandaBottomSheetContentType.MandaDetail ->
                            upsertMandaDetail(contentType.mandaUI.copy(name = inputText))

                        else -> //TODO 여기
                            upsertMandaKey(
                                contentType.mandaUI.copy(
                                    name = inputText,
                                    darkColor = Color.DarkGray,
                                    lightColor = Color.DarkGray,
                                )
                            )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier.padding(end = 5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HMColor.SubText),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { }
                    ) {
                        Text(text = "삭제", style = HmStyle.text16, color = HMColor.Primary)
                    }
                    HMButton(
                        text = "저장",
                        clickableState = buttonClickableState,
                        modifier = Modifier.padding(start = 5.dp)
                    ) {
                        when (contentType) {
                            is MandaBottomSheetContentType.MandaDetail ->
                                upsertMandaDetail(contentType.mandaUI.copy(name = inputText))

                            else -> //TODO 여기
                                upsertMandaKey(
                                    contentType.mandaUI.copy(
                                        name = inputText,
                                        darkColor = Color.DarkGray,
                                        lightColor = Color.DarkGray,
                                    )
                                )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MandaBottomSheetColor(
    onClick: (Int) -> Unit
) {
    val colorInfoListState = remember {
        mutableStateListOf(
            MandaColorInfo(HMColor.Dark.Pink, true, 0),
            MandaColorInfo(HMColor.Dark.Red, false, 1),
            MandaColorInfo(HMColor.Dark.Orange, false, 2),
            MandaColorInfo(HMColor.Dark.Yellow, false, 3),
            MandaColorInfo(HMColor.Dark.Green, false, 4),
            MandaColorInfo(HMColor.Dark.Blue, false, 5),
            MandaColorInfo(HMColor.Dark.Indigo, false, 6),
            MandaColorInfo(HMColor.Dark.Purple, false, 7)
        )
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "색상",
            modifier = Modifier.fillMaxWidth(),
            style = HmStyle.text16,
            textAlign = TextAlign.Start
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(colorInfoListState.size) { index ->
                RoundButton(colorInfoListState[index]) {
                    onClick(index)
                    colorInfoListState.forEachIndexed { colorIndex, colorInfo ->
                        if(colorIndex == index)
                            colorInfoListState[index] = colorInfoListState[index].copy(isChecked = true)
                        else
                            colorInfo.isChecked = false
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
            .size(50.dp)
            .background(
                if (colorInfo.isChecked) colorInfo.color else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.size(40.dp),
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
fun MandaBottomSheetDone(
    checkedState: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "달성")
        HMSwitch(checked = checkedState) {
            onClick(!checkedState)
        }
    }
}

@Preview
@Composable
fun BottomSheetPreview() {
    MandaBottomSheetDone(false) {}
}