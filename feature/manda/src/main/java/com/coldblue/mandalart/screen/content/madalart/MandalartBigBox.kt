package com.coldblue.mandalart.screen.content.madalart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.screen.MandaEmptyBox
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaState

@Composable
fun MandalartBigBox(
    modifier: Modifier,
    mandaState: MandaState,
    isZoom: Boolean,
    isMandaInit: Boolean,
    onZoom: () -> Unit,
    onInsert: (Boolean, MandaBottomSheetContentState) -> Unit,
    onDialog:()->Unit,
    onInit:()->Unit,
    isMandaCenter:Boolean,
    setMandaDoneState:(Int, Boolean, Color)->Unit,
) {
    Box(modifier.padding(horizontal = 5.dp).clip(RoundedCornerShape(8))){
        when(mandaState){
            is MandaState.Empty -> {
                onInit()
                MandaEmptyBox(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (isMandaInit) {
                        onInsert(
                            true,
                            MandaBottomSheetContentState.Insert(
                                MandaBottomSheetContentType.MandaKey(
                                    MandaUI(id = mandaState.id),
                                    null
                                )
                            )
                        )
                    } else {
                        onDialog()
                    }

                }
            }
            is MandaState.Exist->{
                MandalartSmallGrid(
                    mandaState = mandaState,
                    onInsert = onInsert,
                    isMandaInit = isMandaInit,
                    onDialog = onDialog,
                    isMandaCenter  =isMandaCenter,
                    setMandaDoneState=setMandaDoneState
                )

            }
        }
        if (!isZoom) {
            Spacer(modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .aspectRatio(1F)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                        onZoom()
                }
            )
        }

    }

}