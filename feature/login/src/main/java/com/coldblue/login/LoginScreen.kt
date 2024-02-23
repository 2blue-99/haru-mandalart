package com.coldblue.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.LoginButton
import com.coldblue.login.state.UiState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    loginViewModel.loginState.collectAsStateWithLifecycle().value.let {
        when(it){
            UiState.Success -> Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
            UiState.Fail -> Toast.makeText(context, "로그인 실패..", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    val authState = loginViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result -> loginViewModel.checkLoginState(result) },
        fallback = {}
    )

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier.padding(bottom = 80.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = com.coldblue.designsystem.R.drawable.pupple_icon),
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