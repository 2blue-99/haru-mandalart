package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.Manda
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMandaUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
){
    operator fun invoke(): Flow<List<Manda>> =
        mandaRepo.getMandaRepo()
}