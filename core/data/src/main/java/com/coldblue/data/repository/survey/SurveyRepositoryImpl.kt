package com.coldblue.data.repository.survey

import com.coldblue.data.mapper.SurveyMapper.asDomain
import com.coldblue.model.Survey
import com.coldblue.network.datasource.SurveyDataSource
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDataSource: SurveyDataSource
) : SurveyRepository {
    override suspend fun getSurveyList(): List<Survey> {
        val surveyLikedList = surveyDataSource.getSurveyLikedList()
        return surveyDataSource.getSurveyList().asDomain(surveyLikedList.map { it.survey_id })
    }

    override suspend fun getSurvey(id: Int): Survey {
        val surveyLiked = surveyDataSource.isSurveyLiked(id)
        return surveyDataSource.getSurvey(id).asDomain(surveyLiked)

    }

    override suspend fun likeSurvey(id: Int, likeCount: Int) {
        surveyDataSource.likeSurvey(id, likeCount)
    }

    override suspend fun likeCancelSurvey(id: Int, likeCount: Int) {
        surveyDataSource.likeCancelSurvey(id, likeCount)

    }
}