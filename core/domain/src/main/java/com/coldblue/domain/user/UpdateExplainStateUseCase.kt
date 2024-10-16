package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import javax.inject.Inject

class UpdateExplainStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(state: Boolean){
        userRepository.updateExplain(state)
    }
}