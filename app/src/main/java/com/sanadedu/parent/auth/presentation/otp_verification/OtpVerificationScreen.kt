package com.sanadedu.parent.auth.presentation.otp_verification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.components.MainButton
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    email: String,
    onNavigateUp: () -> Unit,
    onVerified: (String) -> Unit,
    viewModel: CodeVerificationViewModel = viewModel()
) {
    var otpValue by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailParts = email.split("@")

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
    ) {
        BasicAppBar(
            navigateUp = onNavigateUp,
            text = stringResource(id = R.string.email_verification)
        )

        Box(modifier = Modifier.weight(3f)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title text
                Text(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.enter_otp),
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Description text (optional)
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = emailParts[0].substring(0, 3) + "xxx@" + emailParts[1], // Customize message
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )


                OtpInputField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .focusRequester(focusRequester),
                    otpText = otpValue,
                    shouldCursorBlink = true,
                    shouldShowCursor = true,
                    onOtpModified = { value, otpFilled ->
                        otpValue = value
                        isOtpFilled = otpFilled
                        if (otpFilled) {
                            keyboardController?.hide()
                        }
                    }
                )
            }
        }

        // Verify button
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                MainButton(
                    modifier = Modifier,
                    text = stringResource(id = R.string.verify),
                    onClick = {
                        viewModel.onEvent(
                            event = OtpVerificationPageEvent.VerifyOtp(
                                email = email,
                                otp = otpValue
                            ),
                            callback = { isVerified ->
                                if (isVerified) {
                                    onVerified(email)
                                }
                            }
                        )
                    }
                )

                // Resend button (optional)
                ResendOtpButton(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    viewModel.onEvent(
                        OtpVerificationPageEvent.ReSendOtp(email = email),
                        callback = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ResendOtpButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    var seconds by remember { mutableIntStateOf(60) }

    LaunchedEffect(key1 = seconds) {
        if (seconds != 0) {
            delay(1000)
            seconds -= 1
        }
    }

    TextButton(
        modifier = modifier,
        enabled = seconds == 0,
        onClick = {
            seconds = 60
            onClick()
        }
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.resend_otp))
                if (seconds != 0) {
                    append(" - $seconds ")
                    append(stringResource(id = R.string.seconds))
                }
            },
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            color = DeepBlue,
            fontSize = 16.sp
        )
    }
}
