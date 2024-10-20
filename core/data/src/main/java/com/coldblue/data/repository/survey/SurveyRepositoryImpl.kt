package com.coldblue.data.repository.survey

import com.coldblue.data.mapper.SurveyMapper.asDomain
import com.coldblue.data.mapper.SurveyMapper.asNetwork
import com.coldblue.model.Survey
import com.coldblue.model.SurveyComment
import com.coldblue.network.datasource.SurveyDataSource
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource
) : SurveyRepository {
    override suspend fun getSurveyList(): List<Survey> {
        val surveyLikedList = surveyDataSource.getSurveyLikedList()
        val surveyCommentList = surveyDataSource.getAllSurveyCommentList()

        return surveyDataSource.getSurveyList()
            .asDomain(surveyLikedList.map { it.survey_id }, commentCount = surveyCommentList)
    }

    override suspend fun getSurveyCommentList(id: Int): List<SurveyComment> {
        val commentList = surveyDataSource.getSurveyCommentList(id)
        val uniqueUserIds = commentList.map { it.user_id }.distinct()

        val userIdMapping =
            uniqueUserIds.withIndex().associate { (index, userId) -> userId to "${index + 1}" }

        val updatedComments = commentList.map { comment ->
            comment.copy(user_id = userIdMapping[comment.user_id] ?: comment.user_id)
        }

        return updatedComments.asDomain()
    }

    override suspend fun upsertSurveyCommentList(surveyComment: SurveyComment) {
        surveyDataSource.upsertSurveyComment(surveyComment.asNetwork())
    }

    override suspend fun upsertSurvey(survey: Survey) {
        surveyDataSource.upsertSurvey(survey.asNetwork())
    }

    override suspend fun getSurvey(id: Int): Survey {
        val surveyLiked = surveyDataSource.isSurveyLiked(id)
        return surveyDataSource.getSurvey(id).asDomain(surveyLiked, getSurveyCommentList(id).size)

    }

    override suspend fun likeSurvey(id: Int, likeCount: Int) {
        surveyDataSource.likeSurvey(id, likeCount)
    }

    override suspend fun likeCancelSurvey(id: Int, likeCount: Int) {
        surveyDataSource.likeCancelSurvey(id, likeCount)

    }
}