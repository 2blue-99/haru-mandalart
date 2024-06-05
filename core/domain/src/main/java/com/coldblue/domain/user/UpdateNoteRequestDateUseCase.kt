package com.coldblue.domain.user

import com.coldblue.data.repository.user.UserRepository
import javax.inject.Inject

class UpdateNoteRequestDateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: String){
        userRepository.updateNoteRequestDate(token)
    }
}