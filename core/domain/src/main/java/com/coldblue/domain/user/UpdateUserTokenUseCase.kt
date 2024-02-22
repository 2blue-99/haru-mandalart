package com.coldblue.domain.user

import com.coldblue.data.repo.UserRepo
import javax.inject.Inject

class UpdateUserTokenUseCase @Inject constructor(
    private val userRepo: UserRepo
) {
    suspend operator fun invoke(token: String){
        userRepo.updateToken(token)
    }
}