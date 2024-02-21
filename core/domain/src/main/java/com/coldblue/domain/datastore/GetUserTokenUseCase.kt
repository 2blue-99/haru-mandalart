package com.coldblue.domain.datastore

import com.coldblue.data.repo.UserPreferencesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val userPreferencesRepo: UserPreferencesRepo
){
    operator fun invoke(): Flow<String> =
        userPreferencesRepo.token
}