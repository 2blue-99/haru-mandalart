package com.coldblue.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.R
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.login.state.LoginUiState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val loginUiState by loginViewModel.loginState.collectAsStateWithLifecycle()

    when (val state = loginUiState) {
        is LoginUiState.Fail -> Toast.makeText(
            context,
            "실패 : ${state.loginException.msg}",
            Toast.LENGTH_SHORT
        ).show()

        else -> {}
    }
    val authState = loginViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result -> loginViewModel.checkLoginState(result) },
        fallback = { }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(7f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f),
                painter = painterResource(id = com.coldblue.login.R.drawable.app_icon),
                contentDescription = "앱 아이콘"
            )
            Column(
                Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "하루,", style = HmStyle.text30, color = HMColor.Primary)
                Text(text = "만다라트", style = HmStyle.text30, color = HMColor.Primary)
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            LoginButton { authState.startFlow() }

        }
    }
}


@Composable
fun LoginButton(
    onClick: () -> Unit
) {
    Button(
        border = BorderStroke(1.dp, HMColor.Gray),
        onClick = { onClick() },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = HMColor.Background,
            contentColor = HMColor.Text
        ),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                color = HMColor.Text,
                text = "Google로 시작하기",
                style = TextStyle.Default,
                fontWeight = FontWeight.Medium
            )
        }
    }
}