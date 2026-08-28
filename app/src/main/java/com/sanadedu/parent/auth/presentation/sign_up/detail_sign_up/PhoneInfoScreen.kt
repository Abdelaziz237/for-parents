package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.CustomProgressBar
import com.sanadedu.parent.auth.presentation.LabelText
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.info.RegisterFormEvent
import com.sanadedu.parent.core.domain.usecases.ShowToast
import com.sanadedu.parent.core.presentation.components.MainButton
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.White

@Composable
fun PhoneInfoScreen(
    viewModel: DetailSignUpViewModel,
    onSubmit: (phoneNumber: String) -> Unit
) {
    val state = viewModel.formState.value
    var phoneNumber by remember {
        mutableStateOf(state.phoneNumber)
    }

    Column(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp)
        .fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(3f)) {
            Column {
                CustomProgressBar(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .height(8.dp)
                        .fillMaxWidth(),
                    percent = 0.25f
                )

                LabelText(
                    modifier = Modifier.padding(top = 48.dp),
                    text = R.string.phone_number_question_header
                )

                LoadBody(
                    modifier = Modifier.padding(top = 20.dp),
                    onPhoneNumberEntered = { value ->
                        phoneNumber = value
                    }
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            MainButton(
                modifier = Modifier,
                text = stringResource(id = R.string.next),
                onClick = {
                    if (phoneNumber.length == 11) onSubmit(phoneNumber)
                    else ShowToast().invoke(R.string.invalid_phone_number, ToastType.ErrorToast)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier,
    onValueChanged: (phoneNumber: String) -> Unit
) {
    var phoneNumber by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = modifier,
        value = phoneNumber,
        onValueChange = {
            phoneNumber = it
            onValueChanged(it)
        },
        singleLine = true,
        placeholder = {
            Text(
                text = "enter your phone number",
                color = HintTextColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = DeepBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            color = TextColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
}

@Composable
fun LoadBody(
    modifier: Modifier,
    onPhoneNumberEntered: (phoneNumber: String) -> Unit,
) {
    var modifierState by remember {
        mutableStateOf(
            modifier.border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(16.dp)
            )
        )
    }
    Row(
        modifier = modifierState.background(color = White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .height(20.dp)
                .width(24.dp)
                .clip(shape = RoundedCornerShape(4.dp)),
            painter = painterResource(id = R.drawable.img_egy_flag),
            contentDescription = stringResource(id = R.string.egy_flag),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = R.string.country_code),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            color = TextColor
        )
        PhoneNumberTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp)
                .onFocusChanged {
                    if (it.hasFocus) {
                        modifierState = modifier.border(
                            width = 1.dp,
                            color = DeepBlue,
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                },
            onValueChanged = { phoneNumber ->
                onPhoneNumberEntered(phoneNumber)
            },
        )
    }
}
