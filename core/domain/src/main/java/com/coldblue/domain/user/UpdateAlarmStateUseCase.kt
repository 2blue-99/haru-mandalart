package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import javax.inject.Inject

class UpdateAlarmStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(state: Boolean){
        userRepository.updateAlarm(state)
    }
}