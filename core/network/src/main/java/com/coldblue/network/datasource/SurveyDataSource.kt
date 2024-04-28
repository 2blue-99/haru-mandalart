package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyLike

interface SurveyDataSource {
    suspend fun getSurveyList(): List<NetworkSurvey>
    suspend fun getSurveyLikedList(): List<NetworkSurveyLike>

    suspend fun getSurvey(id: Int): NetworkSurvey
    suspend fun isSurveyLiked(id: Int): Boolean

    suspend fun likeSurvey(id: Int, likeCount: Int)
    suspend fun likeCancelSurvey(id: Int, likeCount: Int)
}