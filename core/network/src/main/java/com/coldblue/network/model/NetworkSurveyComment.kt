package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkSurveyComment(
    val id: Int = 0,
    val survey_id: Int,
    val user_id: String="",
    val date: String,
    val comment: String,

)
