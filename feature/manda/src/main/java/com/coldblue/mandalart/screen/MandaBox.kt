package com.coldblue.mandalart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Plus
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle

@Composable
fun MandaBaseBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = HMColor.Primary,
    borderColor: Color = HMColor.Gray,
    borderWidth: Dp = 0.65.dp,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1F)
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
    ){
        content()
    }
}

@Preview
@Composable
fun BaseBoxPreview() {
    MandaBaseBox()
}

@Composable
fun MandaEmptyBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    MandaBaseBox(
        modifier = modifier,
        backgroundColor = HMColor.Background,
        onClick = onClick
    ){
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .scale(0.35f),
            imageVector = IconPack.Plus,
            tint = HMColor.SubLightText,
            contentDescription = ""
        )
    }
}
@Preview
@Composable
fun MandaEmptyBox2Preview() {
    MandaEmptyBox()
}

@Composable
fun MandaKeyBox(
    modifier: Modifier = Modifier,
    name: String = "Title",
    backgroundColor: Color = HMColor.Primary,
    borderColor: Color = HMColor.Primary,
    isDone: Boolean = true,
    isCenter : Boolean = false,
    onClick: () -> Unit = {},
){
    MandaBaseBox(
        modifier = modifier,
        backgroundColor = if (isDone) backgroundColor else HMColor.Background,
        borderColor = if(isDone) HMColor.Background else borderColor,
        borderWidth = if(isCenter && isDone) 0.dp else 1.5.dp,
        onClick = onClick
    ){
        Text(
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            color = if (isDone) HMColor.Background else backgroundColor,
            text = name,
            style = HmStyle.text6,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun MandaKeyBox2Preview() {
    MandaKeyBox()
}

@Composable
fun MandaDetailBox(
    modifier: Modifier = Modifier,
    name: String = "Title",
    backgroundColor: Color = HMColor.Background,
    borderColor: Color = HMColor.Background,
    isDone: Boolean = false,
    onClick: () -> Unit = {}
){
    MandaBaseBox(
        modifier = modifier,
        backgroundColor = if (isDone) backgroundColor else HMColor.Background,
        borderColor = if(isDone) HMColor.Background else borderColor,
        borderWidth = if(isDone) 0.dp else 0.65.dp,
        onClick = onClick
    ){
        Text(
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            color = if (isDone) HMColor.Background else HMColor.Text,
            text = name,
            style = HmStyle.text6,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun MandaDetailBox2Preview() {
    MandaDetailBox()
}


//@Composable
//fun MandaKeyBox(
//    name: String = "Title",
//    color: Color = HMColor.Primary,
//    isDone: Boolean = true,
//    onClick: () -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() }
//            .fillMaxWidth()
//            .aspectRatio(1F)
//            .border(BorderStroke(1.5.dp, color), RoundedCornerShape(8.dp))
//            .background(if (isDone) color else HMColor.Background)
//    ) {
//        Text(
//            modifier = Modifier.padding(4.dp),
//            textAlign = TextAlign.Center,
//            color = if (isDone) HMColor.Background else color,
//            text = name,
//            style = HmStyle.text6,
//            fontWeight = FontWeight.Bold,
//            overflow = TextOverflow.Ellipsis,
//        )
//    }
//}
//
//@Preview
//@Composable
//fun MandaKeyBoxPreview() {
//    MandaKeyBox() {}
//}



//@Composable
//fun MandaDetailBox(
//    name: String = "Title",
//    color: Color = HMColor.Background,
//    isDone: Boolean = false,
//    onClick: () -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() }
//            .fillMaxWidth()
//            .aspectRatio(1F)
//            .border(0.65.dp, color = color, RoundedCornerShape(8.dp))
//            .background(if (isDone) color else HMColor.Background)
//    ) {
//        Text(
//            textAlign = TextAlign.Center,
//            text = name,
//            color = if (isDone) HMColor.Background else HMColor.Text,
//            modifier = Modifier.padding(2.dp),
//            style = HmStyle.text6,
//            overflow = TextOverflow.Ellipsis,
//        )
//    }
//}
//
//@Preview
//@Composable
//fun MandaDetailBoxPreview() {
//    MandaDetailBox() {}
//}
//
//
//@Composable
//fun MandaEmptyBox(
//    onClick: () -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() }
//            .fillMaxWidth()
//            .aspectRatio(1F)
//            .background(HMColor.Background)
//            .border(0.65.dp, HMColor.Gray, RoundedCornerShape(8.dp))
//    ) {
//        Icon(
//            modifier = Modifier
//                .fillMaxSize()
//                .scale(0.35f),
//            imageVector = IconPack.Plus,
//            tint = HMColor.SubLightText,
//            contentDescription = ""
//        )
//    }
//}
//
//
//@Preview
//@Composable
//fun DetailBoxPreview() {
//    MandaEmptyBox() {}
//}