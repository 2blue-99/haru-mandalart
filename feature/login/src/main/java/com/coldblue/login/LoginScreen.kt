package com.coldblue.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.LoginButton
import com.coldblue.designsystem.icon.HMIcon
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val authState = loginViewModel.composeAuth.rememberSignInWithGoogle(
        onResult = { result -> loginViewModel.checkLoginState(result) },
        fallback = {}
    )

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
//
//            Icon(painter = painterResource(id = HMIcon.purple_login_icon), modifier = Modifier.size(200.dp,200.dp), contentDescription = null)
//        }
        Box(
            modifier = Modifier.padding(bottom = 80.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = HMIcon.purple_login_icon),
                tint = Color(0xFF432ED1),
                modifier = Modifier.size(200.dp, 200.dp),
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier.padding(bottom = 80.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            LoginButton { authState.startFlow() }
        }
    }
}