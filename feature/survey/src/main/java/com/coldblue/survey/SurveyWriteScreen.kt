package com.coldblue.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldblue.data.util.getDateString
import com.coldblue.designsystem.IconPack
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.designsystem.iconpack.todo.AddSquare
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Survey
import com.coldblue.model.SurveyComment


@Composable
fun SurveyWriteScreen(
    navigateToBackstack: () -> Unit,
    surveyWriteViewModel: SurveyWriteViewModel = hiltViewModel(),
) {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            HMTopBar(title = "제안하기") {
                navigateToBackstack()
            }
            Column (modifier = Modifier.padding(16.dp)){
                Text(text = "제목", style = HmStyle.text20)
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = title, onValueChange = { title = it },
                    maxLines = 2,
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = HMColor.Box,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(all = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                                innerTextField()
                            }
                        }
                    },
                )

                Text(text = "본문", style = HmStyle.text20)
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = content, onValueChange = { content = it },
                    minLines = 8,
                    maxLines = 16,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = HMColor.Box,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(all = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                                innerTextField()
                            }
                        }
                    },
                )

            }

        }

        HMButton(
            text = "완료",
            clickableState = title.isNotEmpty() and content.isNotEmpty(),
            modifier = Modifier.padding(16.dp)
        ) {
            surveyWriteViewModel.upsertSurvey(
                Survey(
                    title = title,
                    content = content,
                    state = "기능제안",
                    date = getDateString(),
                    likeCount = 0,
                    isLiked = false,
                    commentCount = 0,
                    userType = "사용자"
                )
            )
            title = ""
            content = ""
            navigateToBackstack()
        }
    }
}