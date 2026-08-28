package com.sanadedu.parent.auth.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.BasicTextField
import com.sanadedu.parent.auth.presentation.PasswordTextField
import com.sanadedu.parent.auth.presentation.sign_in.data.AuthError
import com.sanadedu.parent.auth.presentation.sign_in.info.LoginPageEvent
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.bl

@Composable
fun SignInScreen(
    createNewAccount: () -> Unit,
    forgotPassword: () -> Unit,
    toOtpVerification: () -> Unit,
    toCompleteRegistration: (email: String) -> Unit,
    onSignedIn: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: SignInViewModel = viewModel()
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val isLoading = viewModel.state.value.isLoading
    Box(modifier = Modifier.imePadding()) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
        ) {
            BasicAppBar(
                navigateUp = onNavigateUp,
                text = stringResource(id = R.string.sign_in))

            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = bl,
                    trackColor = LightGray,
                    strokeCap = StrokeCap.Round
                )
            }
            Box(modifier = Modifier
                .weight(3f)
                .padding(top = 24.dp)) {
                Column {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 20.dp),
                        label = stringResource(id = R.string.email),
                        onValueChanged = { value ->
                            email = value
                        }
                    )
                    PasswordTextField(
                        onValueChanged = { value ->
                            password = value
                        }
                    )
                    TextButton(
                        modifier = Modifier.padding(start = 16.dp),
                        onClick = { forgotPassword() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = DeepBlue
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column (
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 16.dp, end = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepBlue
                        ),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            viewModel.onEvent(LoginPageEvent.StartLoading)
                            viewModel.onEvent(
                                event = LoginPageEvent.Login(email, password),
                                onSuccess = onSignedIn,
                                onError = { cause ->
                                    when (cause) {
                                        AuthError.INCOMPLETE_REGISTRATION -> {
                                            toCompleteRegistration(email)
                                        }
                                        AuthError.NEED_OTP_VERIFICATION -> {
                                            toOtpVerification()
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_in),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                    Row (modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.dont_have_account),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = DeepBlue
                        )
                        TextButton(
                            onClick = {
                                createNewAccount()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.create_new_account),
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = HintTextColor
                            )
                        }
                    }
                }
            }
        }
    }
}