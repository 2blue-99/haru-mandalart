package com.coldblue.mandalart.screen.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.colddelight.mandalart.R
import kotlin.math.roundToInt

@Composable
fun MandaStatus(
    titleName: String,
    statusColor: Color,
    donePercentage: Float,
    animateDonePercentage: Float,
    onClickTitle: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(text = "현재 만다 $currentManda")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "\"",
                style = HmStyle.text24,
                color = statusColor
            )
            ClickableText(
                modifier = Modifier.widthIn(max = (screenWidth - 60).dp),
                text = AnnotatedString(titleName.ifEmpty {
                    stringResource(id = R.string.initialized_empty_title)
                }),
                onClick = { onClickTitle() },
                style = HmStyle.text24.copy(color = statusColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "\"",
                style = HmStyle.text24,
                color = statusColor
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        id = R.string.initialized_done_percentage,
                        "${((donePercentage * 100).roundToInt())}%"
                    ),
                    style = HmStyle.text12,
                    color = statusColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                LinearProgressIndicator(
                    progress = { animateDonePercentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(7.dp)),
                    color = statusColor,
                    trackColor = HMColor.Gray
                )
            }
        }
    }
}