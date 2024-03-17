package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmStateUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(): Flow<Boolean> =
        userRepository.isAlarm
}