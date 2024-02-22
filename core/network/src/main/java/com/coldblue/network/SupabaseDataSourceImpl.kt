package com.coldblue.network

import com.coldblue.network.model.MandaModel
import com.coldblue.network.model.TodoModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class SupabaseDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : SupabaseDataSource {
    override val clientToken: String? = client.auth.currentAccessTokenOrNull()
    override val composeAuth: ComposeAuth = client.composeAuth

    override suspend fun upsertTodoData(data: List<TodoModel>): List<Int> =
        client.postgrest["todo"].upsert<TodoModel>(data, onConflict = "id") { select() }
            .decodeList<TodoModel>()
            .map { it.id }
    override suspend fun insertMandalartData(data: MandaModel) {
        client.postgrest["mada"].insert(data)
    }
    override suspend fun readUpdatedData(date: String): List<TodoModel> =
        client.from("todo").select {
            filter {
                TodoModel::update_date_time gt date
            }
        }.decodeList<TodoModel>()
    override suspend fun deleteMandalartData(id: Int) {
        client.from("Mandalart").delete {
            filter {
                MandaModel::id eq 666
                eq("id", id)
            }
        }
    }
}