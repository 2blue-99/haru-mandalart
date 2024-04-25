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
}