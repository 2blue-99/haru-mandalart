package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.SurveyDataSource
import com.coldblue.network.model.NetworkNotice
import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyLike
import com.orhanobut.logger.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class SurveyDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : SurveyDataSource {
    override suspend fun getSurveyList(): List<NetworkSurvey> {
        return client.postgrest["survey"].select {
            order(
                column = "id",
                order = Order.DESCENDING
            )
        }.decodeList<NetworkSurvey>()
    }

    override suspend fun getSurveyLikedList(): List<NetworkSurveyLike> {
        return client.postgrest["surveyLike"].select {
            order(
                column = "survey_id",
                order = Order.DESCENDING
            )
        }.decodeList<NetworkSurveyLike>()
    }

    override suspend fun getSurvey(id: Int): NetworkSurvey {
        return client.postgrest["survey"].select {
            filter {
                NetworkNotice::id eq id
            }
        }.decodeSingle<NetworkSurvey>()
    }

    override suspend fun upsertLike(id: Int, likeCount: Int) {
        client.postgrest["survey"].update(
            {
                NetworkSurvey::like_count setTo likeCount
            }
        ) {
            filter {
                NetworkSurvey::id eq id
            }
        }
    }

}