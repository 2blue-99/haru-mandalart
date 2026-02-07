package com.coldblue.mandalart.screen.content.madalart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaState

@Composable
fun MandalartSmallGrid(
    mandaState: MandaState.Exist,
    isMandaInit: Boolean,
    onInsert: (Boolean, MandaBottomSheetContentState) -> Unit,
    onDialog:()->Unit,
    isMandaCenter:Boolean,
    setMandaDoneState:(Int, Boolean, Color)->Unit,
    ) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(3) { row ->
            Row {
                repeat(3) { col ->
                    MandalartSmallBox(
                        smallBox = mandaState.mandaUIList[col + row * 3],
                        bigBoxId = mandaState.id,
                        onInsert = onInsert,
                        isMandaInit = isMandaInit,
                        modifier = Modifier.weight(1f),
                        onDialog = onDialog,
                        isMandaCenter = isMandaCenter,
                        setMandaDoneState = setMandaDoneState
                    )
                }
            }
        }
    }
}