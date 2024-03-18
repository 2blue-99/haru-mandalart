package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.MandaKeyDataSource
import com.coldblue.network.model.NetworkId
import com.coldblue.network.model.NetworkMandaKey
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class MandaKeyDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : MandaKeyDataSource {
    override suspend fun getMandaKey(update: String): List<NetworkMandaKey> {
        return client.postgrest["mandaKey"].select {
            filter {
                NetworkMandaKey::update_time gt update
            }
        }.decodeList<NetworkMandaKey>()
    }

    override suspend fun upsertMandaKey(mandaKeys: List<NetworkMandaKey>): List<Int> {
        val result =
            client.postgrest["mandaKey"].upsert(mandaKeys, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }
}