package com.coldblue.mandalart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMSwitch
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.designsystem.iconpack.todo.CircleCheck
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.mandalart.model.asMandaDetail
import com.coldblue.mandalart.model.asMandaKey
import com.coldblue.mandalart.state.MAX_MANDA_CNT
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaChangeInfo
import com.coldblue.mandalart.util.MandaUtils
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandaBottomSheet(
    mandaBottomSheetContentState: MandaBottomSheetContentState,
    sheetState: SheetState,
    mandaKeyList: List<String>,
    usedColorIndexList: List<Int>,
    upsertMandaFinal: (MandaKey) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    onDisMiss: () -> Unit
) {

    val contentType = mandaBottomSheetContentState.mandaBottomSheetContentType
    val mandaUI = contentType.mandaUI

    val otherMandaKeyList =
        mandaKeyList - listOf(mandaBottomSheetContentState.mandaBottomSheetContentType.mandaUI.name).toSet()

    var colorList = listOf(
        HMColor.DarkPastel.Pink,
        HMColor.DarkPastel.Red,
        HMColor.DarkPastel.Orange,
        HMColor.DarkPastel.Yellow,
        HMColor.DarkPastel.Green,
        HMColor.DarkPastel.Blue,
        HMColor.DarkPastel.Mint,
        HMColor.DarkPastel.Purple
    )


    var inputText by remember { mutableStateOf(mandaUI.name) }
    var keyNameText by remember { mutableStateOf("") }

    // insert : 사용안한거 선택 / 만약 다 사용중일 경우엔 첫번째꺼 선택
    // update : 클릭된거 선택
    var colorIndex by remember {
        val color = MandaUtils.colorToIndex(mandaUI.color)
        val ableList = colorList.indices.filter { !usedColorIndexList.contains(it) }
        val index = if (ableList.isEmpty()) 0 else if (color == -1) ableList.first() else color
        mutableIntStateOf(index)
    }

    var buttonClickableState by remember { mutableStateOf(mandaUI.name.isNotBlank()) }
    var doneCheckedState by remember { mutableStateOf(mandaUI.isDone) }
    var dialogState by remember { mutableStateOf(false) }
    var duplicatedState by remember { mutableStateOf(false) }

    if (dialogState) {
        HMTextDialog(
            targetText = keyNameText,
            bottomText = stringResource(id = R.string.dialog_main_title),
            confirmText = stringResource(id = R.string.bottom_sheet_delete),
            onDismissRequest = {
                dialogState = false
                onDisMiss()
            },
            tintColor = mandaUI.color,
            onConfirm = {
                deleteMandaKey(
                    mandaUI.id,
                    (contentType as MandaBottomSheetContentType.MandaKey).groupIdList ?: emptyList()
                )
                onDisMiss()
            })

    }


    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = sheetState,
        containerColor = HMColor.Background
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = contentType.title,
                style = HmStyle.text20,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp)
            ) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_name),
                    style = HmStyle.text16,
                    fontWeight = FontWeight.Bold
                )
                HMTextField(inputText = inputText, maxLen = contentType.maxLen) {
                    inputText = it
                    buttonClickableState = inputText.isNotBlank()
                    duplicatedState = false
                }
                if (duplicatedState)
                    Text(
                        text = stringResource(id = R.string.bottom_sheet_exist_goal),
                        style = HmStyle.text12,
                        color = HMColor.NegativeText
                    )
            }

            when (contentType) {
                is MandaBottomSheetContentType.MandaFinal ->
                    Spacer(modifier = Modifier.height(70.dp))

                is MandaBottomSheetContentType.MandaKey ->
                    MandaBottomSheetColor(
                        colorList,
                        colorIndex,
                        usedColorIndexList,
                    ) {
                        colorIndex = it
                    }

                is MandaBottomSheetContentType.MandaDetail ->
                    MandaBottomSheetDone(doneCheckedState) { doneCheckedState = it }
            }

            when (mandaBottomSheetContentState) {

                is MandaBottomSheetContentState.Insert -> {
                    HMButton(
                        text = stringResource(id = com.coldblue.designsystem.R.string.all_save),
                        clickableState = buttonClickableState
                    ) {
                        when (contentType) {

                            is MandaBottomSheetContentType.MandaFinal ->
                                if (otherMandaKeyList.contains(inputText))
                                    duplicatedState = true
                                else {
                                    upsertMandaFinal(mandaUI.asMandaKey(inputText, colorIndex))
                                    onDisMiss()
                                }

                            is MandaBottomSheetContentType.MandaKey ->
                                if (otherMandaKeyList.contains(inputText))
                                    duplicatedState = true
                                else {
                                    upsertMandaKey(mandaUI.asMandaKey(inputText, colorIndex))
                                    onDisMiss()
                                }

                            is MandaBottomSheetContentType.MandaDetail -> {
                                upsertMandaDetail(
                                    mandaUI.asMandaDetail(
                                        inputText,
                                        doneCheckedState,
                                        colorIndex
                                    )
                                )
                                onDisMiss()
                            }
                        }
                    }
                }

                is MandaBottomSheetContentState.Update -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxSize()
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
                                text = stringResource(id = R.string.bottom_sheet_delete),
                                style = HmStyle.text16,
                                color = HMColor.Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        HMButton(
                            text = stringResource(id = com.coldblue.designsystem.R.string.all_save),
                            clickableState = buttonClickableState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                        ) {
                            when (contentType) {
                                is MandaBottomSheetContentType.MandaKey -> {
                                    if (otherMandaKeyList.contains(inputText))
                                        duplicatedState = true
                                    else {
                                        upsertMandaKey(mandaUI.asMandaKey(inputText, colorIndex))
                                        onDisMiss()
                                    }
                                }

                                else -> {
                                    upsertMandaDetail(
                                        mandaUI.asMandaDetail(
                                            inputText,
                                            doneCheckedState,
                                            colorIndex
                                        )
                                    )
                                    onDisMiss()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeMandaBottomSheet(
    mandaChangeInfo: List<MandaChangeInfo>,
    currentMandaIndex: Int,
    changeManda: (Int) -> Unit,
    onDisMiss: () -> Unit,
    deleteManda: (Int) -> Unit
) {
    var mandaDialogState by remember { mutableStateOf(false) }
    var deleteIndex by remember { mutableIntStateOf(currentMandaIndex) }

    if (mandaDialogState) {
        HMTextDialog(
            targetText = mandaChangeInfo[deleteIndex].name,
            bottomText = "해당 만다라트의 모든 정보가 삭제되요.",
            confirmText = stringResource(id = R.string.bottom_sheet_delete),
            onDismissRequest = {
                mandaDialogState = false
            },
            tintColor = HMColor.LightPastel.Red,
            onConfirm = {
                deleteManda(deleteIndex)
                mandaDialogState = false
            })

    }
    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        containerColor = HMColor.Background
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = "만다라트 변경",
                style = HmStyle.text20,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            LazyColumn {
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "${mandaChangeInfo.filter { !it.isEmpty }.size}/$MAX_MANDA_CNT")
                    }
                }
                for (i in 0 until MAX_MANDA_CNT) {
                    item {
//                        val mandaIndex = if (i < mandaInfo.size) (mandaInfo[i].index - 5) / 9 else i
                        if (mandaChangeInfo[i].isEmpty) {
                            MandaEmptyItem(
                                changeManda = { changeManda(i) },
                                onDisMiss
                            )

                        } else {
                            MandaItem(
                                mandaChangeInfo[i].name,
                                currentMandaIndex == i,
                                { changeManda(i) },
                                onDisMiss,
                                {
                                    deleteIndex = i
                                    mandaDialogState = true
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun MandaItem(
    text: String,
    isSelected: Boolean,
    changeManda: () -> Unit,
    onDisMiss: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onDisMiss()
                changeManda()
            }
            .border(
                width = 2.dp,
                color = if (isSelected) HMColor.Primary else HMColor.LiteGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.LiteGray)
            .padding(16.dp)
    ) {
        Text(
            text = text,
        )
        if (isSelected) {
            Icon(
                imageVector = IconPack.CircleCheck,
                modifier = Modifier.background(HMColor.Primary, shape = CircleShape),
                tint = HMColor.LiteGray,
                contentDescription = "checkbox"
            )
        } else {
            Icon(
                modifier = Modifier.clickable {
                    onDelete()
                },
                imageVector = Icons.Default.Delete,
                tint = HMColor.DarkGray,
                contentDescription = "delete"
            )
        }
    }

}

@Composable
fun MandaEmptyItem(
    changeManda: () -> Unit,
    onDisMiss: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onDisMiss()
                changeManda()
            }
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.LiteGray)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            tint = HMColor.Primary,
            contentDescription = "plus"
        )
    }

}

@Preview
@Composable
fun MandaItemPreview() {
    MandaItem("test", true, {}, {}, {})
}


@Composable
fun MandaBottomSheetColor(
    colorList: List<Color>,
    colorIndex: Int,
    usedColorList: List<Int>,
    onClick: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_sheet_color),
            modifier = Modifier.fillMaxWidth(),
            style = HmStyle.text16,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(colorList.size / 4) { column ->
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
                            HMColorButton(
                                color = colorList[btnIndex],
                                isUsed = usedColorList.contains(btnIndex),
                                isClick = btnIndex == colorIndex,
                            ) {
                                onClick(btnIndex)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun HMColorButton(
    color: Color,
    isUsed: Boolean,
    isClick: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = { onClick() },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {}
        if (isUsed) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = HMColor.Background,
                    modifier = Modifier
                        .fillMaxSize()
                        .size(50.dp),
                    contentDescription = "Used"
                )
            }
        }
        if (isClick) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .border(3.dp, HMColor.Background, CircleShape)
            ) {}
        }
    }
}

@Composable
fun MandaBottomSheetDone(
    checkedState: Boolean,
    onClick: (Boolean) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.bottom_sheet_achieve_state),
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
                text = if (checkedState)
                    stringResource(id = R.string.bottom_sheet_achieve) else
                    stringResource(id = R.string.bottom_sheet_not_achieve),
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
fun RoundButtonPreview() {
    HMColorButton(
        HMColor.DarkPastel.Pink,
        true,
        true,
    ) {}
}

//@Preview
//@Composable
//fun AlertDialogPreview() {
//    MandaKeyDialog("asdads", onDisMiss = {}, onDelete = {})
//}