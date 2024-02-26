package com.coldblue.domain.user

import com.coldblue.data.repo.UserRepository
import javax.inject.Inject

class UpdateMandaInitStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: Boolean){
        userRepository.updateInit(token)
    }
}