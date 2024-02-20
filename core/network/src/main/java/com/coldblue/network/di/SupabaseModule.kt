package com.coldblue.network.di

import com.coldblue.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Singleton
    @Provides
    fun provideSupabaseClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = BuildConfig.SupaUrl,
        supabaseKey = BuildConfig.SupaKey
    ){
        install(Auth)
        install(ComposeAuth){ googleNativeLogin(serverClientId = BuildConfig.SupaId) }
        install(Postgrest)
    }

    @Singleton
    @Provides
    fun provideComposeAuth(client: SupabaseClient): ComposeAuth = client.composeAuth

//    @Singleton
//    @Provides
//    fun provideSupabaseRepo()
}