package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaKeyRepository
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKeyMandaUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository
){
    operator fun invoke(): Flow<List<MandaKey>> =
        mandaKeyRepository.getMandaKeys()
}