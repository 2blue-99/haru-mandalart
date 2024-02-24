package com.coldblue.login.exception

import com.coldblue.login.state.LoginExceptionState

fun String.exceptionHandler(): LoginExceptionState {
    return when(this){
        "error: operation canceled" -> LoginExceptionState.DropDown()
        "10: Caller not whitelisted to call this API." -> LoginExceptionState.Waiting()
        "16: Caller has been temporarily blocked due to too many canceled sign-in prompts." -> LoginExceptionState.Waiting()
        else -> LoginExceptionState.Unknown("알 수 없는 오류 : $this")
    }
}