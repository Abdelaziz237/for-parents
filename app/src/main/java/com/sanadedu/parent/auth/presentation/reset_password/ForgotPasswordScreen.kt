package com.sanadedu.parent.auth.presentation.reset_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.bl

@Composable
fun ForgotPasswordScreen(
    onNavigateUp: () -> Unit,
    onPasswordReset: () -> Unit,
    viewModel: ResetPasswordViewModel = viewModel(modelClass = ResetPasswordViewModel::class.java)
) {
    val isLoading = viewModel.state.value
    var email by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.imePadding()) {
        Column {
            BasicAppBar(
                navigateUp = onNavigateUp,
                text = stringResource(id = R.string.forgot_password)
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

            Column(modifier = Modifier.fillMaxHeight(0.75f)) {
                Text(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    text = stringResource(id = R.string.forgot_password_message),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = TextColor
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
            }
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
                    viewModel.onEvent(event = ForgotPasswordPageEvents.StartLoading)
                    viewModel.onEvent(
                        event = ForgotPasswordPageEvents.SendResetPasswordLink(
                            email = email
                        ),
                        callback = { isValid ->
                            if (isValid) onPasswordReset()
                        }
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

