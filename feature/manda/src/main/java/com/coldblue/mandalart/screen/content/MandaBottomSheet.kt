package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.coldblue.mandalart.state.BottomSheetContentState
import com.coldblue.mandalart.state.BottomSheetContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandaBottomSheet(
    bottomSheetContentState: BottomSheetContentState,
    sheetState: SheetState,
    upsertMandaKey: (MandaUI) -> Unit,
    upsertMandaDetail: (MandaUI) -> Unit,
    onDisMiss: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var colorIndex by remember { mutableIntStateOf(0) }
    var buttonClickableState by remember { mutableStateOf(false) }
    var doneCheckedState by remember { mutableStateOf(false) }

    val contentType = bottomSheetContentState.bottomSheetContentType

    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(text = contentType.title)

            Text(text = contentType.title + " 이름")

            HMTextField {
                inputText = it
                if (inputText.isBlank()) buttonClickableState = false
            }

            if (contentType is BottomSheetContentType.MandaKey) {
                MandaBottomSheetColor {
                    colorIndex = it
                }
            }

            if (contentType is BottomSheetContentType.MandaDetail) {
                MandaBottomSheetDone(doneCheckedState) {
                    doneCheckedState = it
                }
            }

            if (bottomSheetContentState is BottomSheetContentState.Insert) {
                HMButton(text = "저장", clickableState = buttonClickableState) {
                    when (contentType) {
                        is BottomSheetContentType.MandaDetail ->
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
                            is BottomSheetContentType.MandaDetail ->
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
            items(8) {
                RoundButton(Color.Black) {
                    onClick(it)
                }
            }
//            HMColor.Dark::class.java.declaredFields.forEach {
//                item {
//                    RoundButton(it.get(it) as Color)
//                }
//            }
        }
    }
}

@Composable
fun RoundButton(
    color: Color,
    onClick: () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(if (isClicked) 50.dp else 40.dp)
            .background(color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.size(40.dp),
            onClick = {
                onClick()
                isClicked = !isClicked
            },
            shape = CircleShape,
            border = if (isClicked) BorderStroke(2.dp, HMColor.Background) else null,
            colors = ButtonDefaults.buttonColors(containerColor = color)
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
    MandaBottomSheetDone(false){}
}