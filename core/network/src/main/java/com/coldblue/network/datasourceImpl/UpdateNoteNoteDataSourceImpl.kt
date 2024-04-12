package com.coldblue.network.datasourceImpl

import com.coldblue.network.datasource.UpdateNoteDataSource
import com.coldblue.network.model.NetWorkUpdateNote
import com.coldblue.network.model.NetworkMandaKey
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class UpdateNoteNoteDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : UpdateNoteDataSource {

    override suspend fun getUpdateNote(): List<NetWorkUpdateNote> {
        return client.postgrest["updateNote"].select {
//            filter {
//                NetWorkUpdateNote::id
//            }
        }.decodeList<NetWorkUpdateNote>()
    }
}