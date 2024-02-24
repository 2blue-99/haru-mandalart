package com.coldblue.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.R
import com.coldblue.login.state.LoginExceptionState
import com.coldblue.login.state.LoginUiState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val loginUiState by loginViewModel.loginState.collectAsStateWithLifecycle()
    when (val state = loginUiState) {
        is LoginUiState.Fail -> {
            Toast.makeText(context, "실패 : ${state.loginException.msg}", Toast.LENGTH_SHORT).show()
//            when(state.loginException){
//                is LoginExceptionState.Waiting->  Toast.makeText(context, "실패 : ${state.loginException.msg}", Toast.LENGTH_SHORT).show()
//                is LoginExceptionState.Unknown->  Toast.makeText(context, "실패 : ${state.loginException.msg}", Toast.LENGTH_SHORT).show()
//                is LoginExceptionState.DropDown->  {}
//            }
        }
        else -> {}
    }

    val authState = loginViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result -> loginViewModel.checkLoginState(result) },
        fallback = { }
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
                painter = painterResource(id = R.drawable.pupple_icon),
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

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.DarkGray),
        onClick = { onClick() },
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(22.dp, 22.dp),
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                color = Color.DarkGray,
                text = "Google 계정으로 시작하기"
            )
        }
    }
}