package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMTextField
import com.coldblue.mandalart.state.BottomSheetContentState
import com.coldblue.mandalart.state.BottomSheetContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandaBottomSheet(
    bottomSheetContentState: BottomSheetContentState,
    onDisMiss: () -> Unit
){
    val bottomSheetState = rememberModalBottomSheetState()

    val contentType = bottomSheetContentState.bottomSheetContentType

    ModalBottomSheet(
        onDismissRequest = { onDisMiss() },
        sheetState = bottomSheetState
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

            Text(text = contentType.title +" 이름")

            HMTextField{

            }

            if(contentType is BottomSheetContentType.MandaKey){

            }

            if(contentType is BottomSheetContentType.MandaDetail){
                
            }

            if(bottomSheetContentState is BottomSheetContentState.Insert) {
                // 저장 버튼 하나만 생성
            }else{
                // 삭제, 저장 버튼 생성
            }
        }
    }

}

//@Preview
//@Composable
//fun BottomSheetPreview(){
//    MandaBottomSheet(null){}
//}