package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.MandaCombine
import com.coldblue.model.MandaKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMandaUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
){
    operator fun invoke(): Flow<MandaCombine> =
        mandaRepo.getAllMandaRepository()
}