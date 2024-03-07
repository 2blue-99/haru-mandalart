package com.coldblue.mandalart.screen

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    var colorIndex by remember { mutableIntStateOf(MandaUtils.colorToIndex(mandaUI.darkColor)) }
    var buttonClickableState by remember { mutableStateOf(mandaBottomSheetContentState is MandaBottomSheetContentState.Update) }
    var doneCheckedState by remember { mutableStateOf(mandaUI.isDone) }



    Log.e("TAG", "MandaBottomSheet: $mandaUI")

    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = sheetState
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
                textAlign = TextAlign.Center
            )

            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp)
            ) {
                Text(text = contentType.title + " 이름", style = HmStyle.text16)
                HMTextField(inputText = inputText) {
                    inputText = it
                    buttonClickableState = inputText.isNotBlank()
                }
            }

            when (contentType) {
                is MandaBottomSheetContentType.MandaFinal ->
                    Spacer(modifier = Modifier.height(70.dp))

                is MandaBottomSheetContentType.MandaKey ->
                    MandaBottomSheetColor { colorIndex = it }

                is MandaBottomSheetContentType.MandaDetail ->
                    MandaBottomSheetDone(doneCheckedState) { doneCheckedState = it }
            }

            when (mandaBottomSheetContentState) {
                is MandaBottomSheetContentState.Insert -> {
                    HMButton(text = "저장", clickableState = buttonClickableState) {
                        when (contentType) {
                            is MandaBottomSheetContentType.MandaDetail ->
                                upsertMandaDetail(
                                    mandaUI.asMandaDetail(
                                        inputText,
                                        doneCheckedState,
                                        colorIndex
                                    )
                                )

                            else ->
                                upsertMandaKey(mandaUI.asMandaKey(inputText, colorIndex))
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
                            colors = ButtonDefaults.buttonColors(containerColor = HMColor.SubText),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                when (contentType) {
                                    is MandaBottomSheetContentType.MandaDetail ->
                                        deleteMandaDetail(mandaUI.id)

                                    is MandaBottomSheetContentType.MandaKey ->
                                        deleteMandaKey(
                                            mandaUI.id,
                                            contentType.groupIdList ?: emptyList()
                                        )

                                    is MandaBottomSheetContentType.MandaFinal -> {}
                                }
                                onDisMiss()
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
                        if (colorIndex == index)
                            colorInfoListState[index] =
                                colorInfoListState[index].copy(isChecked = true)
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