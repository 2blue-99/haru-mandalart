package com.coldblue.data.mapper

import com.coldblue.model.Survey
import com.coldblue.model.SurveyComment
import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyComment

object SurveyMapper {
    fun List<NetworkSurvey>.asDomain(
        liked: List<Int>,
        commentCount: List<NetworkSurveyComment>
    ): List<Survey> {
        return this.map { survey ->
            survey.asDomain(
                liked.contains(survey.id),
                commentCount.count { it.survey_id == survey.id })
        }
    }

    fun List<NetworkSurveyComment>.asDomain(): List<SurveyComment> {
        return this.map {
            it.asDomain()
        }
    }

    fun NetworkSurvey.asDomain(isLiked: Boolean, commentCount: Int): Survey {
        return Survey(
            id,
            title,
            state,
            date,
            like_count,
            content,
            if (is_admin) "관리자" else "사용자",
            isLiked,
            commentCount
        )
    }

    fun NetworkSurveyComment.asDomain(): SurveyComment {
        return SurveyComment(
            id,
            survey_id,
            user_id,
            date,
            comment
        )
    }

    fun SurveyComment.asNetwork(): NetworkSurveyComment {
        return NetworkSurveyComment(
            survey_id = surveyId,
            date = date,
            comment = comment
        )
    }

    fun Survey.asNetwork(): NetworkSurvey {
        return NetworkSurvey(
            id,
            state,
            title,
            date,
            content,
            false,
            likeCount
        )
    }
}