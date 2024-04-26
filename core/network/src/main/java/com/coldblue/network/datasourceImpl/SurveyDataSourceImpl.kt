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
        return try {
            client.postgrest["survey"].select {
                order(
                    column = "date",
                    order = Order.DESCENDING
                )
            }.decodeList<NetworkSurvey>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSurveyLikedList(): List<NetworkSurveyLike> {
        return try {
            client.postgrest["surveyLike"].select().decodeList<NetworkSurveyLike>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSurvey(id: Int): NetworkSurvey {
        return try {
            client.postgrest["survey"].select {
                filter {
                    NetworkNotice::id eq id
                }
            }.decodeSingle<NetworkSurvey>()
        } catch (e: Exception) {
            NetworkSurvey(0, "", "", "", "", true, 0)
        }
    }

    override suspend fun isSurveyLiked(id: Int): Boolean {
        return try {
            client.postgrest["surveyLike"].select {
                filter {
                    NetworkSurveyLike::survey_id eq id
                }
            }.decodeList<NetworkSurveyLike>().isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun likeSurvey(id: Int, likeCount: Int) {
        try {
            client.postgrest["survey"].update(
                {
                    NetworkSurvey::like_count setTo likeCount
                }
            ) {
                filter {
                    NetworkSurvey::id eq id
                }
            }

            val like = NetworkSurveyLike(survey_id = id)
            client.postgrest["surveyLike"].insert(like)

        } catch (e: Exception) {
        }
    }

    override suspend fun likeCancelSurvey(id: Int, likeCount: Int) {
        try {
            client.postgrest["survey"].update(
                {
                    NetworkSurvey::like_count setTo likeCount
                }
            ) {
                filter {
                    NetworkSurvey::id eq id
                }
            }
            client.postgrest["surveyLike"].delete {
                filter {
                    NetworkSurveyLike::survey_id eq id
                }
            }
        } catch (e: Exception) {
        }
    }
}