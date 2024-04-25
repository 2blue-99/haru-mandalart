package com.coldblue.survey

import com.coldblue.model.Survey

sealed interface SurveyUiState {
    data object Loading : SurveyUiState

    data class Error(val msg: String) : SurveyUiState

    data class Success(
        val surveyList: List<Survey>
    ) : SurveyUiState

}