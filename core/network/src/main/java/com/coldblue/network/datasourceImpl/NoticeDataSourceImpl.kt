package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.NoticeDataSource
import com.coldblue.network.model.NetworkMandaKey
import com.coldblue.network.model.NetworkNotice
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : NoticeDataSource {
    override suspend fun getNoticeList(): List<NetworkNotice> {
        return client.postgrest["notice"].select(columns = Columns.list("id, title, date")) {
            order(
                column = "id",
                order = Order.DESCENDING
            )
        }.decodeList<NetworkNotice>()
    }

    override suspend fun getNotice(id: Int): NetworkNotice {
        return client.postgrest["notice"].select {
            filter {
                NetworkNotice::id eq id
            }
        }.decodeSingle<NetworkNotice>()
    }
}