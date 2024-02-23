//package com.coldblue.domain.user
//
//import com.coldblue.data.repo.UserRepo
//import com.coldblue.data.util.LoginHelper
//import com.coldblue.data.util.LoginHelperImpl
//import io.github.jan.supabase.compose.auth.ComposeAuth
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class GetComposeAuthUseCase @Inject constructor(
//    private val loginHelperImpl: LoginHelper
//){
//    operator fun invoke(): ComposeAuth = loginHelperImpl.composeAuth
//}