package com.coldblue.setting.exception

import com.coldblue.setting.R
import com.coldblue.setting.state.LoginExceptionState


fun String.exceptionHandler(): LoginExceptionState {
    return when(this){
        "error: operation canceled" -> LoginExceptionState.DropDown()
        "10: Caller not whitelisted to call this API." -> LoginExceptionState.Waiting()
        "16: Caller has been temporarily blocked due to too many canceled sign-in prompts." -> LoginExceptionState.Waiting()
        else -> LoginExceptionState.Unknown("${R.string.unknown_err} : $this")
    }
}