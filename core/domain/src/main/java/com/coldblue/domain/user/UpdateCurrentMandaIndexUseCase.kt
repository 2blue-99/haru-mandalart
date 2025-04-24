package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import javax.inject.Inject

class UpdateCurrentMandaIndexUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(index: Int) {
        userRepository.updateCurrentMandaIndex(index)
    }
}