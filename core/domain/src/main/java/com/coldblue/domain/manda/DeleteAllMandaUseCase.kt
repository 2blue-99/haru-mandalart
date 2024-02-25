package com.coldblue.domain.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.KeyManda
import javax.inject.Inject

class DeleteAllMandaUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
) {
    suspend operator fun invoke(data:KeyManda){
        mandaRepo.deleteAllMandaRepo(data)
    }
}