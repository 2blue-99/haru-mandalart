package com.coldblue.network

import com.coldblue.network.model.NetworkMandaDetail
import com.coldblue.network.model.NetworkMandaKey
import com.coldblue.network.model.NetworkSurvey
import com.coldblue.network.model.NetworkSurveyComment
import com.coldblue.network.model.NetworkSurveyLike
import com.coldblue.network.model.NetworkTodo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseDataSourceImpl @Inject constructor(
    private val client: SupabaseClient,
) : SupabaseDataSource {
    override suspend fun deleteUser() {
        val user = client.auth.currentUserOrNull()
        if (user != null) {
            client.postgrest["todo"].delete {
                filter {
                    NetworkTodo::user_id eq user.id
                }
            }
            client.postgrest["mandaTodo"].delete {
                filter {
                    NetworkTodo::user_id eq user.id
                }
            }
            client.postgrest["mandaKey"].delete {
                filter {
                    NetworkMandaKey::user_id eq user.id
                }
            }
            client.postgrest["mandadetail"].delete {
                filter {
                    NetworkMandaDetail::user_id eq user.id
                }
            }
            client.postgrest["mandaTodo"].delete {
                filter {
                    NetworkMandaDetail::user_id eq user.id
                }
            }
            client.postgrest["survey"].delete {
                filter {
                    NetworkSurvey::user_id eq user.id
                }
            }
            client.postgrest["surveyLike"].delete {
                filter {
                    NetworkSurveyLike::user_id eq user.id
                }
            }
            client.postgrest["surveyComment"].delete {
                filter {
                    NetworkSurveyComment::user_id eq user.id
                }
            }
        }
    }

    override suspend fun refresh(token: String): Flow<Boolean> {
        return try {
            if (client.auth.currentAccessTokenOrNull() == null) {
                client.auth.retrieveUser(token)
                client.auth.refreshCurrentSession()
            }
            flow { emit(true) }
        } catch (e: Exception) {
            flow { emit(false) }

        }

    }
}