package com.coldblue.domain.network

import com.coldblue.data.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNetworkStateUseCase @Inject constructor(
    private val networkHelper: NetworkHelper
) {
    operator fun invoke(): Flow<Boolean> = networkHelper.isOnline

}