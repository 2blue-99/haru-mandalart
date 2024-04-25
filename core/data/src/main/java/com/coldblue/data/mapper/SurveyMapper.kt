package com.coldblue.data.mapper

import com.coldblue.model.Survey
import com.coldblue.network.model.NetworkSurvey

object SurveyMapper {
    fun List<NetworkSurvey>.asDomain(liked: List<Int>): List<Survey> {
        return this.map { it.asDomain(liked.contains(it.id)) }
    }

    fun NetworkSurvey.asDomain(isLiked: Boolean): Survey {
        return Survey(
            id,
            title,
            state,
            date,
            like_count,
            content,
            if (is_admin) "관리자" else "사용자",
            isLiked
        )
    }
}