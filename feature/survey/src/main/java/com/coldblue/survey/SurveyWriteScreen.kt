package com.coldblue.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldblue.data.util.getDateString
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.model.Survey


@Composable
fun SurveyWriteScreen(
    navigateToBackstack: () -> Unit,
    surveyWriteViewModel: SurveyWriteViewModel = hiltViewModel(),
) {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        HMTopBar(title = "제안하기") {
            navigateToBackstack()
        }
        Text(text = "쓰는곳")
        TextField(value = title, onValueChange = { title = it })
        TextField(value = content, onValueChange = { content = it })

        Button(onClick = {
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
        }

        ) {
        }
    }
}