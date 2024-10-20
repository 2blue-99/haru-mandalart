package com.coldblue.model

data class SurveyComment(
    val id: Int = 0,
    val surveyId: Int,
    val userId: String = "",
    val date: String,
    val comment: String,

    )
