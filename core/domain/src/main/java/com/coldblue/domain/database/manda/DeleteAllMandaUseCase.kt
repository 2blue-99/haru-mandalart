package com.coldblue.domain.database.manda

import com.coldblue.data.repo.MandaRepo
import com.coldblue.model.Manda
import javax.inject.Inject

class DeleteAllMandaUseCase @Inject constructor(
    private val mandaRepo: MandaRepo
) {
    suspend operator fun invoke(data:Manda){
        mandaRepo.deleteAllMandaRepo(data)
    }
}