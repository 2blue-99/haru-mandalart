package com.coldblue.survey.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.data.util.getDateString
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.TopSpacer
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Survey
import com.coldblue.designsystem.R.drawable.comment
import com.coldblue.designsystem.iconpack.todo.AddSquare
import com.coldblue.model.MandaTodo
import com.coldblue.model.SurveyComment
import java.time.LocalDate

@Composable
fun SurveyDetailContent(
    survey: Survey,
    surveyCommentList: List<SurveyComment>,
    updateSurvey: (survey: Survey) -> Unit,
    upsertSurveyComment: (surveyComment: SurveyComment) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val hintVisible by remember {
        derivedStateOf { text.isEmpty() }
    }
    val focusManager = LocalFocusManager.current


    Box(modifier = Modifier.fillMaxSize(), Alignment.BottomCenter) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .imePadding()
                .background(HMColor.Background)
                .padding(16.dp)
        ) {
            item {
                SurveyStateChip(survey.state)
                Text(text = survey.title, style = HmStyle.text20)
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "${survey.userType} | ${survey.date}",
                    style = HmStyle.text16,
                    color = HMColor.SubDarkText
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = survey.content, style = HmStyle.text16
                )
            }
            item {
                HorizontalDivider(color = HMColor.Box)
                TopSpacer()
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = Color.Transparent
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HMColor.Box,
                        contentColor = HMColor.Text,
                        disabledContainerColor = HMColor.Box
                    ),
                    onClick = { updateSurvey(survey) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "같이 응원하기")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                tint = if (survey.isLiked) HMColor.NegativeText else HMColor.Text,
                                imageVector = if (survey.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "좋아요"
                            )
                            Text(text = survey.likeCount.toString())
                        }

                    }
                }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Icon(
                        tint = HMColor.Text,
                        painter = painterResource(id = comment),
                        contentDescription = "댓글"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${survey.commentCount}", style = HmStyle.text20)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "댓글", style = HmStyle.text20)
                    Spacer(modifier = Modifier.width(4.dp))
                }

            }

            items(surveyCommentList) {
                SurveyCommentItem(it, {})
            }
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            value = text, onValueChange = { text = it },
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = HMColor.Box, shape = RoundedCornerShape(size = 8.dp))
                        .padding(all = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                        if (hintVisible) {
                            Text(
                                text = "댓글 작성하기", color = HMColor.DarkGray
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        modifier = Modifier.clickable(enabled = !hintVisible) {
                            upsertSurveyComment(
                                SurveyComment(surveyId = survey.id, comment = text, date = getDateString())
                            )
                            text = ""
                            focusManager.clearFocus()
                        },
                        imageVector = IconPack.AddSquare,
                        contentDescription = "",
                        tint = if (hintVisible) HMColor.DarkGray else HMColor.Primary,
                    )

                }
            },
        )
    }

}


@Composable
fun SurveyCommentItem(
    surveyComment: SurveyComment,
    deleteComment: (surveyComment: SurveyComment) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "사용자 ${surveyComment.userId}  ")
            Text(
                text = surveyComment.date, style = HmStyle.text12,
                color = HMColor.SubDarkText
            )
        }
        Text(
            text = surveyComment.comment,
            style = HmStyle.text16,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        HorizontalDivider(color = HMColor.Box)
    }
}

@Preview
@Composable
fun SurveyDetailContentPreview() {
    SurveyDetailContent(
        survey =
        Survey(2, "다크모드 지원", "진행중", "2024-05-11", 1, "다크모드가 있으면 좋을거 같아요.", "관리자", true, 3),
        surveyCommentList = listOf(SurveyComment(1, 1, "1", "2024/01/11", "댓글1111")), {},{}
    )
}