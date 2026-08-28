package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.CustomProgressBar
import com.sanadedu.parent.auth.presentation.LabelText
import com.sanadedu.parent.core.presentation.components.CustomCheckbox
import com.sanadedu.parent.core.presentation.components.MainButton
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.White
import com.sanadedu.parent.theme.WhiteGray
import com.sanadedu.parent.theme.bl
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun BirthdateInfoScreen(
    viewModel: DetailSignUpViewModel,
    onSubmit: (birthdate: String, gender: String) -> Unit
) {
    val notEntered = stringResource(id = R.string.not_entered)
    val state = viewModel.formState.value
    var birthdate by remember{
        mutableStateOf(if (state.birthDate == "") notEntered else state.birthDate)
    }
    var gender by remember {
        mutableStateOf(state.gender)
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
                    percent = 0.75f
                )

                LabelText(
                    modifier = Modifier.padding(top = 48.dp),
                    text = R.string.birthdate_question_header
                )

                BirthdateTextField(
                    modifier = Modifier.padding(top = 36.dp),
                    value = birthdate,
                    onValueChanged = { value ->
                        birthdate = value
                    }
                )

                LabelText(
                    modifier = Modifier.padding(top = 48.dp),
                    text = R.string.gender_question_header
                )

                GenderSelectionContainer(
                    modifier = Modifier.padding(top = 16.dp),
                    value = gender,
                    onValueChanged = { selectedGender ->
                        gender = selectedGender
                    }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            MainButton(
                modifier = Modifier,
                text = stringResource(id = R.string.next),
                onClick = {
                    onSubmit(birthdate, gender)
                }
            )
        }
    }
}

@Composable
fun GenderSelectionContainer(
    modifier: Modifier,
    value: String,
    onValueChanged: (gender: String) -> Unit
) {
    var isMaleChecked by remember {
        mutableStateOf(false)
    }

    var isFemaleChecked by remember {
        mutableStateOf(false)
    }

    val male = "M"
    val female = "F"

    var checkedGender by remember {
        mutableStateOf(value)
    }
    Row(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCheckbox(
                modifier = Modifier
                    .size(24.dp)
                    .offset(y = 4.dp),
                isEditable = isFemaleChecked.xor(isMaleChecked).not().or(isMaleChecked)
            ) { isSelected ->
                isMaleChecked = isSelected
                if (isSelected) {
                    checkedGender = male
                    onValueChanged(male)
                }
                else onValueChanged("n")
            }
            Text(
                modifier = Modifier.offset(y = 3.dp),
                text = stringResource(id = R.string.male),
                color = TextColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
        }

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCheckbox(
                modifier = Modifier
                    .size(24.dp)
                    .offset(y = 4.dp),
                isEditable = isMaleChecked.xor(isFemaleChecked).not().or(isFemaleChecked)
            ) { isSelected ->
                isFemaleChecked = isSelected
                if (isSelected) {
                    checkedGender = female
                    onValueChanged(female)
                }
                else onValueChanged("n")
            }
            Text(
                modifier = Modifier.offset(y = 3.dp),
                text = stringResource(id = R.string.female),
                color = TextColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BirthdateTextField(
    modifier: Modifier,
    value: String,
    onValueChanged: (String) -> Unit
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var birthdate by remember {
        mutableStateOf(value)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .border(
                    width = 1.dp,
                    color = LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .align(Alignment.CenterStart),
                text = birthdate,
                color = TextColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Image(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { showDatePicker = !showDatePicker },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                contentDescription = stringResource(id = R.string.calender))
        }
        Spacer(
            modifier = Modifier
                .width(56.dp)
                .height(2.dp)
                .offset(x = 18.dp, y = 0.dp)
                .background(WhiteGray)
        )
        Text(
            modifier = Modifier
                .offset(x = 20.dp, y = (-12).dp)
                .background(Color.Transparent),
            text = stringResource(id = R.string.birthdate),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = HintTextColor
        )

        if (showDatePicker) {
            CustomDatePicker(
                onDatePicked = { date ->
                    birthdate = date
                    onValueChanged(date)
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    onDatePicked: (String) -> Unit
) {
    val notEntered = stringResource(id = R.string.not_entered)
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDatePicked(
                            state.selectedDateMillis?.let {
                                convertMillisToDate(it)
                            } ?: notEntered
                        )
                    }
                ) {
                    Text(
                        "OK",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = DeepBlue
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(
                        "CANCEL",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = HintTextColor
                    )
                }
            },
        ) {
            DatePicker(
                state = state,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    titleContentColor = TextColor,
                    headlineContentColor = DeepBlue,
                    selectedDayContainerColor = bl,
                    selectedDayContentColor = Color.White,
                    todayDateBorderColor = DeepBlue,
                    todayContentColor = DeepBlue,

                )
            )
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}