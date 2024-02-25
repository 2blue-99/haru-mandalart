package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.MandaKey
import javax.inject.Inject

class InsertMandaUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
) {
    suspend operator fun invoke(list: List<MandaKey>){
        mandaRepo.upsertMandaRepo(list)
    }
}