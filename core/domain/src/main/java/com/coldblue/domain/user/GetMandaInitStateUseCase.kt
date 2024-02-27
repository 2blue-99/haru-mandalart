package com.coldblue.domain.user

import com.coldblue.data.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMandaInitStateUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(): Flow<Boolean> =
        userRepository.isInit
}