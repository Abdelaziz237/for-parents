package com.sanadedu.parent.auth.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.BasicTextField
import com.sanadedu.parent.auth.presentation.PasswordTextField
import com.sanadedu.parent.auth.presentation.sign_in.data.AuthError
import com.sanadedu.parent.auth.presentation.sign_up.info.SignUpPageEvent
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.components.CustomCheckbox
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.bl

@Composable
fun SignUpScreen(
    toCompleteRegistration: (email: String) -> Unit,
    toOtpVerification: (email: String) -> Unit,
    hasAccount: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val state = viewModel.registerState.value

    var username by remember {
        mutableStateOf(state.fullname)
    }
    var email by remember {
        mutableStateOf(state.email)
    }
    var password by remember {
        mutableStateOf(state.password)
    }
    var isSubscribedToEmailCampaigns by remember {
        mutableStateOf(state.emailCampaigns)
    }

    val passwordRegex = "^(?=\\S*\\d)(?=\\S*[$!@%&_])\\S{8,30}$".toRegex()

    var hasPasswordValidationError by remember {
        mutableStateOf(false)
    }

    val isLoading = viewModel.state.value.isLoading
    Column(
        modifier = Modifier
//            .imePadding()
            .background(Color.Transparent)
    ) {
        BasicAppBar(
            navigateUp = onNavigateUp,
            text = stringResource(id = R.string.new_account)
        )
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
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(top = 24.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp),
                label = stringResource(id = R.string.username),
                onValueChanged = { value ->
                    username = value
                }
            )
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
                    if (passwordRegex.matches(value)) {
                        password = value
                        hasPasswordValidationError = false
                    } else {
                        hasPasswordValidationError = true
                    }
                }
            )

                    Spacer(modifier = Modifier.height(4.dp))
                    if (hasPasswordValidationError) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(id = R.string.password_validation_error),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = ErrorRed
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                CustomCheckbox(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = 4.dp)
                ) { isAccepted ->
                    isSubscribedToEmailCampaigns = isAccepted
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.terms_message_pt1))
                        withStyle(
                            style = SpanStyle(
                                color = DeepBlue
                            )
                        ) {
                            append(" " + stringResource(id = R.string.terms_message_pt2))
                        }
                    },
                    color = TextColor,
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Column (
            modifier = Modifier.weight(1f),
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
                    viewModel.onEvent(event = SignUpPageEvent.StartLoading)
                    viewModel.onEvent(
                        event = SignUpPageEvent.VerifyUser(
                            fullName = username,
                            email = email,
                            password = password,
                            emailCampaigns = isSubscribedToEmailCampaigns
                        ),
                        onSuccess = {
                            toOtpVerification(email)
                        },
                        onError = { cause ->
                            when (cause) {
                                AuthError.INCOMPLETE_REGISTRATION -> {
                                    toCompleteRegistration(email)
                                }
                                AuthError.NEED_OTP_VERIFICATION -> {
                                    toOtpVerification(email)
                                }
                            }
                        }
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
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
                    text = stringResource(id = R.string.already_have_account),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = DeepBlue
                )
                TextButton(
                    onClick = {
                        hasAccount()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
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