package com.coldblue.data.repository.survey

import com.coldblue.model.Survey
import com.coldblue.model.SurveyComment


interface SurveyRepository {
    suspend fun getSurveyList():List<Survey>
    suspend fun getSurveyCommentList(id:Int):List<SurveyComment>
    suspend fun upsertSurveyCommentList(surveyComment: SurveyComment)
    suspend fun upsertSurvey(survey: Survey)
    suspend fun getSurvey(id:Int):Survey
    suspend fun likeSurvey(id: Int, likeCount: Int)
    suspend fun likeCancelSurvey(id: Int, likeCount: Int)
}