package com.coldblue.data.repository.survey

import com.coldblue.model.Survey


interface SurveyRepository {
    suspend fun getSurveyList():List<Survey>
}