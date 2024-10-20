package com.coldblue.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.data.util.getDateString
import com.coldblue.domain.survey.UpsertSurveyCommentUseCase
import com.coldblue.domain.survey.UpsertSurveyUseCase
import com.coldblue.model.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyWriteViewModel @Inject constructor(
    private val upsertSurveyUseCase: UpsertSurveyUseCase,

    ) : ViewModel() {

        fun upsertSurvey(survey: Survey){
        viewModelScope.launch {
            upsertSurveyUseCase(survey)
            }
        }
    init {
//        viewModelScope.launch {
//            upsertSurveyUseCase(
//                Survey(
//                    title = "추가제",
//                    content = "기능제안 내용",
//                    state = "기능제안",
//                    date = getDateString(),
//                    likeCount = 0,
//                    isLiked = false,
//                    commentCount = 0,
//                    userType = "사용자"
//                )
//            )
//        }
    }

}