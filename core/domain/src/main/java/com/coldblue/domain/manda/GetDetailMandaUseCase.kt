package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaDetailRepository
import com.coldblue.model.MandaDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailMandaUseCase @Inject constructor(
    private val mandaDetailRepository: MandaDetailRepository
){
    operator fun invoke(): Flow<List<MandaDetail>> =
        mandaDetailRepository.getMandaDetails()
}