package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCurrentMandaIndexUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Int> =
        userRepository.currentMandaIndex
}