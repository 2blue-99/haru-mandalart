package com.coldblue.domain.manda

import com.coldblue.data.repository.manda.MandaDetailRepository
import com.coldblue.data.repository.manda.MandaKeyRepository
import com.coldblue.data.repository.todo.MandaTodoRepository
import javax.inject.Inject

class DeleteMandaUseCase @Inject constructor(
    private val mandaKeyRepository: MandaKeyRepository,
    private val mandaDetailRepository: MandaDetailRepository,
    private val mandaTodoRepository: MandaTodoRepository,
) {
    suspend operator fun invoke(mandaIndex:Int) {
        mandaKeyRepository.deleteManda(mandaIndex)
    }
    suspend operator fun invoke() {
        mandaKeyRepository.deleteAllMandaKey()
        mandaDetailRepository.deleteAllMandaDetail()
        mandaTodoRepository.deleteAllMandaTodo()
    }
}


//class DeleteMandaUseCase @Inject constructor(
//    private val mandaKeyRepository: MandaKeyRepository,
//) {
//    suspend operator fun invoke(mandaIndex:Int) {
//        mandaKeyRepository.deleteManda(mandaIndex)
//    }
//}