package com.coldblue.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.explain.ExplainScreen
import com.coldblue.login.state.LoginUiState
import com.orhanobut.logger.Logger
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.NativeSignInState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val loginUiState by loginViewModel.loginState.collectAsStateWithLifecycle()
    val networkState by loginViewModel.isOnline.collectAsStateWithLifecycle()
    var openDialog by remember { mutableStateOf(false) }
    var openExplain by remember { mutableStateOf(false) }

    when (val state = loginUiState) {
        is LoginUiState.Fail -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.login_fail, state.loginException.msg),
                Toast.LENGTH_SHORT
            ).show()
        }
        is LoginUiState.Success -> {
//            Toast.makeText(context, stringResource(id = R.string.login_success), Toast.LENGTH_SHORT)
//                .show()
        }
        is LoginUiState.None -> {}
    }
    val authState = loginViewModel.getComposeAuth().rememberSignInWithGoogle(
        onResult = { result ->
            loginViewModel.checkLoginState(result)
            if (result is NativeSignInResult.Success)
                openExplain = true
        },
        fallback = { }
    )

    if (openDialog) {
        HMTextDialog(
            targetText = "",
            text = stringResource(id = R.string.login_non_member_notice),
            confirmText = stringResource(id = R.string.login_check),
            tintColor = HMColor.Primary,
            subText = stringResource(id = R.string.login_non_member_sub_notice),
            onDismissRequest = { openDialog = false },
            onConfirmation = {
                loginViewModel.loginWithOutAuth()
            }
        )
    }

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
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "앱 아이콘"
            )
            Column(
                Modifier.fillMaxWidth(0.4f)
            ) {
                Text(
                    text = stringResource(id = R.string.login_haru),
                    style = HmStyle.text30,
                    color = HMColor.Primary
                )
                Text(
                    text = stringResource(id = R.string.login_mandalart),
                    style = HmStyle.text30,
                    color = HMColor.Primary
                )
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                LoginButton {
                    if (networkState) {
                        authState.startFlow()
                    } else {
                        Toast.makeText(
                            context,
                            R.string.login_check_connecting,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                NotMemberLoginButton {
                    openDialog = true
                }
            }
        }
    }
}

@Composable
fun NotMemberLoginButton(
    onClick: () -> Unit
) {
    Button(
        border = BorderStroke(1.dp, HMColor.Gray),
        onClick = { onClick() },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = HMColor.Background,
            contentColor = HMColor.Text
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                color = HMColor.SubLightText,
                text = stringResource(id = R.string.login_non_member_start),
                style = TextStyle.Default,
                fontWeight = FontWeight.Medium
            )
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
        contentPadding = PaddingValues(vertical = 14.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = HMColor.Background,
            contentColor = HMColor.Text
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = com.coldblue.designsystem.R.drawable.google_icon),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                color = HMColor.Text,
                text = stringResource(id = com.coldblue.login.R.string.login_google_start),
                style = TextStyle.Default,
                fontWeight = FontWeight.Medium
            )
        }
    }
}