package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.MandaTodoDataSource
import com.coldblue.network.model.NetworkId
import com.coldblue.network.model.NetworkMandaTodo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class MandaTodoDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : MandaTodoDataSource {
    override suspend fun getTodo(update: String): List<NetworkMandaTodo> {
        return client.postgrest["mandaTodo"].select {
            filter {
                NetworkMandaTodo::update_time gt update
            }
        }.decodeList<NetworkMandaTodo>()
    }

    override suspend fun upsertTodo(todos: List<NetworkMandaTodo>): List<Int> {
        val result =
            client.postgrest["mandaTodo"].upsert(todos, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }

}