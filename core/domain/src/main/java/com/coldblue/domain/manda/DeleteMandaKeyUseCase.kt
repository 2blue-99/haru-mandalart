package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaKeyRepository
import javax.inject.Inject

class DeleteMandaKeyUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository,
    private val mandaDetailRepository: MandaDetailRepository
) {
    suspend operator fun invoke(keyId: Int, detailIdList: List<Int>){
        mandaKeyRepository.deleteMandaKeys(listOf(keyId), detailIdList)
    }
}