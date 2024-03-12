package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.TodoDataSource
import com.coldblue.network.model.NetworkId
import com.coldblue.network.model.NetworkTodo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class TodoDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : TodoDataSource {
    override suspend fun getTodo(update: String): List<NetworkTodo> {
        return client.postgrest["todo"].select {
            filter {
                NetworkTodo::update_time gt update
            }
        }.decodeList()
    }

    override suspend fun upsertTodo(todo: List<NetworkTodo>): List<Int> {
        val result =
            client.postgrest["todo"].upsert(todo, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }

}