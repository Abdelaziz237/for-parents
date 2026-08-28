package com.sanadedu.parent.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.bl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextField(
    modifier: Modifier,
    label: String,
    initValue: String = "",
    onValueChanged: (value: String) -> Unit
) {
    var text by remember { mutableStateOf(initValue) }

    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            cursorColor = DeepBlue,
            focusedBorderColor = DeepBlue,
            unfocusedBorderColor = HintTextColor,
            focusedLabelColor = DeepBlue,
            unfocusedLabelColor = HintTextColor,
        ),
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MidNightBlue
        ),
        shape = RoundedCornerShape(16.dp),
        maxLines = 1,
        value = text,
        onValueChange = { value ->
            onValueChanged(value)
            text = value
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    onValueChanged: (value: String) -> Unit
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp),
        label = { Text(text = stringResource(id = R.string.password)) },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            cursorColor = DeepBlue,
            focusedBorderColor = DeepBlue,
            unfocusedBorderColor = HintTextColor,
            focusedLabelColor = DeepBlue,
            unfocusedLabelColor = HintTextColor,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red
        ),
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    imageVector =
                    if (passwordVisibility) {
                        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
                    }else {
                        ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
                    },
                    contentDescription = stringResource(id = R.string.password_toggle)
                )
            }
        },
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MidNightBlue
        ),
        maxLines = 1,
        value = password,
        onValueChange = { value ->
            onValueChanged(value)
            password = value
        }
    )
}

@Composable
fun CustomProgressBar(
    modifier: Modifier, percent: Float
) {
    var parentWidth by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    Box(
        modifier = modifier
            .background(LightGray)
            .onGloballyPositioned {
                parentWidth = with(density) {
                    it.size.width.toDp()
                }
            }
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .height(8.dp)
                .background(Brush.horizontalGradient(listOf(DeepBlue, bl)))
                .width(parentWidth.times(percent))
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    usePlaceholder: Boolean,
    data: String = "",
    onValueChanged: (value: String) -> Unit
) {
    val notEntered = stringResource(id = R.string.not_entered)

    var value by remember {
        if (usePlaceholder) mutableStateOf("")
         else mutableStateOf(if (data == "") notEntered else data)
    }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },
        singleLine = true,
        placeholder = {
            Text(
                text = if (usePlaceholder) notEntered else "",
                color = if (usePlaceholder) HintTextColor else TextColor,
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
fun LabelText(modifier: Modifier, text: Int) {
    Text(
        modifier = modifier,
        text = stringResource(id = text), // Update with your string resource
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        color = TextColor, // Text color
        fontSize = 28.sp,
        lineHeight = 36.sp // Adjust line height as needed
    )
}
