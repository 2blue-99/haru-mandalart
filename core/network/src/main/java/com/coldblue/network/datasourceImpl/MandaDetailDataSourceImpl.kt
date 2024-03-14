package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.MandaDetailDataSource
import com.coldblue.network.model.NetworkId
import com.coldblue.network.model.NetworkMandaDetail
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class MandaDetailDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : MandaDetailDataSource {
    override suspend fun getMandaDetail(update: String): List<NetworkMandaDetail> {
        return client.postgrest["mandaDetail"].select {
            filter {
                NetworkMandaDetail::update_time gt update
            }
        }.decodeList<NetworkMandaDetail>()
    }

    override suspend fun upsertMandaDetail(mandaDetails: List<NetworkMandaDetail>): List<Int> {
        val result =
            client.postgrest["mandaDetail"].upsert(mandaDetails, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }


}