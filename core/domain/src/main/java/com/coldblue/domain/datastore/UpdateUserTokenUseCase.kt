package com.coldblue.domain.datastore

import com.coldblue.data.repo.UserPreferencesRepo
import javax.inject.Inject

class UpdateUserTokenUseCase @Inject constructor(
    private val userPreferencesRepo: UserPreferencesRepo
) {
    suspend operator fun invoke(token: String){
        userPreferencesRepo.updateToken(token)
    }
}