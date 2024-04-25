package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyLike

interface SurveyDataSource {
    suspend fun getSurveyList(): List<NetworkSurvey>
    suspend fun getSurveyLikedList(): List<NetworkSurveyLike>

    suspend fun getSurvey(id: Int): NetworkSurvey

    suspend fun upsertLike(id: Int, likeCount:Int)
}