package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.CurrentGroupDataSource
import com.coldblue.network.model.NetworkCurrentGroup
import com.coldblue.network.model.NetworkId
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class CurrentGroupDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : CurrentGroupDataSource {
    override suspend fun getCurrentGroup(update: String): List<NetworkCurrentGroup> {
        return client.postgrest["currentGroup"].select {
            filter {
                NetworkCurrentGroup::update_time gt update
            }
        }.decodeList<NetworkCurrentGroup>()
    }

    override suspend fun upsertCurrentGroup(currentGroup: List<NetworkCurrentGroup>): List<Int> {
        val result =
            client.postgrest["currentGroup"].upsert(currentGroup, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }


}