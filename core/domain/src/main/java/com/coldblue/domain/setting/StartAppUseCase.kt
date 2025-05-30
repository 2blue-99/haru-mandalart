package com.coldblue.domain.setting

import com.coldblue.data.util.SettingHelper
import javax.inject.Inject

class StartAppUseCase @Inject constructor(
    private val settingHelper: SettingHelper,
) {
    operator fun invoke() = settingHelper.startApp()
}

