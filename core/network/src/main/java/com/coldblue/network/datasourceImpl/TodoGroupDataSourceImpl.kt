package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.TodoGroupDataSource
import com.coldblue.network.model.NetWorkTodoGroup
import com.coldblue.network.model.NetworkId
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class TodoGroupDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : TodoGroupDataSource {
    override suspend fun getTodoGroup(update: String): List<NetWorkTodoGroup> {
        return client.postgrest["todoGroup"].select {
            filter {
                NetWorkTodoGroup::update_time gt update
            }
        }.decodeList<NetWorkTodoGroup>()
    }

    override suspend fun upsertTodoGroup(todoGroup: List<NetWorkTodoGroup>): List<Int> {
        val result =
            client.postgrest["todoGroup"].upsert(todoGroup, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }


}