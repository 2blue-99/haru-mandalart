package com.coldblue.todo

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.iconpack.Circle
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.MandaTodo
import java.time.LocalDate

@Composable
fun MandaTodoList(
    colorList: List<Color?>,
    currentIndex: Int,
    todoRange: Int,
    todoList: List<MandaTodo>,
    todoCnt: Int,
    doneTodoCnt: Int
) {
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TodoRangeSelector()
            Text(text = "Todo:$todoCnt")
        }
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            items(todoList) {
                MandaTodoItem(
                    it,
                    currentIndex,
                    if (currentIndex != -1) colorList[it.mandaIndex - 1]
                        ?: HMColor.Gray else HMColor.Gray
                )
            }
        }
    }
}

@Composable
fun MandaTodoItem(mandaTodo: MandaTodo, currentIndex: Int, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HMColor.Gray)
            .clickable { },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CircleCheckbox(color, false) {

        }
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            text = mandaTodo.title,
            style = HmStyle.text16,
            maxLines = 2
        )
        Box(
            modifier = Modifier
                .padding(end = 0.dp)
                .width(12.dp)
                .height(60.dp)
                .background(
                    color, shape = RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(8.dp),
                        bottomEnd = CornerSize(8.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
        ) {

        }
    }


}

@Composable
fun CircleCheckbox(
    color: Color,
    selected: Boolean,
    enabled: Boolean = true,
    onChecked: () -> Unit
) {

    val imageVector = if (selected) Icons.Filled.CheckCircle else IconPack.Circle
    val tint = if (selected) color.copy(alpha = 0.8f) else HMColor.Gray
    val background = if (selected) color else Color.Black

    IconButton(
        onClick = { onChecked() },
        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
        enabled = enabled
    ) {

        Icon(
            imageVector = imageVector, tint = tint,
            modifier = Modifier.background(background, shape = CircleShape),
            contentDescription = "checkbox"
        )
    }
}

@Composable
fun TodoRangeSelector() {
    Text(text = "오늘, 이번주, 전체")
}

@Preview
@Composable
fun MandaTodoItemPreview() {
//            upsertMandaTodoUseCase(MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false))

    MandaTodoList(
        listOf(HMColor.Manda.Red, HMColor.Manda.Orange),
        1, 2, listOf(
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false),
            MandaTodo("1번투구", false, false, null, LocalDate.now(), 1, false)
        ), 3, 3
    )

}