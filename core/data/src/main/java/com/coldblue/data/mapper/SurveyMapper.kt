package com.coldblue.data.mapper

import com.coldblue.model.Survey
import com.coldblue.model.SurveyComment
import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyComment
import com.coldblue.network.model.NetworkSurveyLike
import com.orhanobut.logger.Logger

object SurveyMapper {
    fun List<NetworkSurvey>.asDomain(
        userId: String,
        liked: List<NetworkSurveyLike>,
        commentCount: List<NetworkSurveyComment>
    ): List<Survey> {
        return this.map { survey ->
            survey.asDomain(
                liked.filter { it.user_id == userId }.size == 1,
                commentCount.count { it.survey_id == survey.id },
                liked.count { it.survey_id == survey.id })
        }
    }

    fun List<NetworkSurveyComment>.asDomain(): List<SurveyComment> {
        return this.map {
            it.asDomain()
        }
    }

    fun NetworkSurvey.asDomain(isLiked: Boolean, commentCount: Int, likeCnt: Int): Survey {
        return Survey(
            id,
            title,
            state,
            date,
            likeCnt,
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