package com.coldblue.mandalart.widget

import com.coldblue.domain.manda.GetDetailMandaUseCase
import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.setting.StartAppUseCase
import javax.inject.Inject


class MandaWidgetViewModel @Inject constructor(
    private val getKeyMandaUseCase: GetKeyMandaUseCase,
    private val getDetailMandaUseCase: GetDetailMandaUseCase,
    private val startAppUseCase: StartAppUseCase

) {
    val mandaKey = getKeyMandaUseCase()
    val mandaDetail = getDetailMandaUseCase()

    fun startApp() {
        startAppUseCase()
    }
}