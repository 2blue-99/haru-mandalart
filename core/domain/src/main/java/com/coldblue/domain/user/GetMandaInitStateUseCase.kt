package com.coldblue.domain.user

import com.coldblue.data.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMandaInitStateUseCase @Inject constructor(
    private val userRepo: UserRepo
){
    operator fun invoke(): Flow<Boolean> =
        userRepo.isInit
}