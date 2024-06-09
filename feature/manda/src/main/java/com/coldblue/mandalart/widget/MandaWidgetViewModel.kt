package com.coldblue.mandalart.widget

import com.coldblue.domain.manda.GetKeyMandaUseCase
import com.coldblue.domain.setting.StartAppUseCase
import javax.inject.Inject


class MandaWidgetViewModel @Inject constructor(
    private val getKeyMandaUseCase: GetKeyMandaUseCase,
    private val startAppUseCase: StartAppUseCase

) {
    val mandaKey = getKeyMandaUseCase()

    fun startApp() {
        startAppUseCase()
    }
}