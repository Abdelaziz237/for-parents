package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.gson.Gson
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.CustomProgressBar
import com.sanadedu.parent.auth.presentation.CustomTextField
import com.sanadedu.parent.auth.presentation.LabelText
import com.sanadedu.parent.core.presentation.components.MainButton
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.White
import com.sanadedu.parent.theme.WhiteGray
import java.io.InputStreamReader

@Composable
fun AddressInfoScreen(
    viewModel: DetailSignUpViewModel,
    onSubmit: (gov:String, city:String, address:String) -> Unit
) {
    val state = viewModel.formState.value
    val notEntered = stringResource(id = R.string.not_entered)

    var gov by remember {
        mutableStateOf(state.governorate)
    }
    var city by remember {
        mutableStateOf(state.city)
    }
    var address by remember {
        mutableStateOf(state.address)
    }

    val context = LocalContext.current
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
                    percent = 0.5f
                )

                LabelText(
                    modifier = Modifier.padding(top = 48.dp),
                    text = R.string.address_question_header
                )

                val govs = mutableListOf(notEntered)
                govs.addAll(loadJsonFromRaw(context = context, R.raw.govs, Govs::class.java)?.govs ?: emptyList())

                LoadDropDownField(
                    modifier = Modifier
                        .heightIn(max = 100.dp)
                        .padding(top = 20.dp)
                        .background(color = White),
                    label = stringResource(id = R.string.government),
                    data = gov,
                    lineWidth = 72.dp,
                    listItems = govs,
                    onItemSelected = { government ->
                        gov = government
                    }
                )

                val cities = mutableListOf(notEntered)
                cities.addAll(loadJsonFromRaw(context = context, R.raw.cities, Cities::class.java)?.cities ?: emptyList())

                LoadDropDownField(
                    modifier = Modifier
                        .heightIn(max = 120.dp)
                        .padding(top = 20.dp)
                        .background(color = White),
                    data = city,
                    label = stringResource(id = R.string.state),
                    lineWidth = 32.dp,
                    listItems = cities,
                    onItemSelected = { state ->
                        city = state
                    }
                )

                AddressTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(56.dp),
                    value = address,
                    onValueChanged = { value ->
                        address = value
                    }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            MainButton(
                modifier = Modifier,
                text = stringResource(id = R.string.next),
                onClick = {
                    onSubmit(gov, city, address)
                }
            )
        }
    }
}

@Composable
fun LoadDropDownField(
    modifier: Modifier,
    data: String,
    label: String,
    lineWidth: Dp,
    listItems: List<String>,
    onItemSelected: (address: String) -> Unit
) {
    val notEntered = stringResource(id = R.string.not_entered)
    var value by remember {
        mutableStateOf(if (data == "") notEntered else data)
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .height(56.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
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
                text = value,
                color = TextColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
        Spacer(
            modifier = Modifier
                .width(lineWidth)
                .height(2.dp)
                .offset(x = 18.dp, y = 0.dp)
                .background(WhiteGray)
        )
        Text(
            modifier = Modifier
                .offset(x = 20.dp, y = (-12).dp)
                .background(Color.Transparent),
            text = label,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = HintTextColor
        )

        PopUpList(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.TopEnd)
                .offset(y = 16.dp),
            itemList = listItems,
            onItemClick = { selectedItem ->
                value = selectedItem
                onItemSelected(selectedItem)
            }
        )
    }
}

@Composable
fun PopUpList(
    itemList: List<String>,
    modifier: Modifier,
    onItemClick: (String) -> Unit
) {
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .height(24.dp)
                .width(40.dp)
                .padding(end = 16.dp)
                .clip(shape = CircleShape)
                .clickable {
                    showDropdown = true
                },
            contentScale = ContentScale.Fit,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_down_arrow),
            contentDescription = stringResource(id = R.string.dropdown)
        )
        if (showDropdown) {
            Popup(
                offset = IntOffset(x = 0, y = 60),
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { showDropdown = false }
            ) {
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .heightIn(max = 120.dp)
                        .padding(end = 36.dp)
                        .verticalScroll(state = scrollState)
                        .background(color = White)
                        .border(width = 1.dp, color = LightGray, shape = RoundedCornerShape(8.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    itemList.onEachIndexed { index, item ->
                        if (index != 0) {
                            Divider(thickness = 1.dp, color = LightGray)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .clickable {
                                    onItemClick(itemList[index])
                                    showDropdown = !showDropdown
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                color = TextColor,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun AddressTextField(
    modifier: Modifier,
    value: String,
    onValueChanged: (address: String) -> Unit
) {
    var modifierState by remember {
        mutableStateOf(
            Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    var textColor by remember {
        mutableStateOf(HintTextColor)
    }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = modifierState.background(color = White)
        ){
            CustomTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusChanged { state ->
                        if (state.hasFocus) {
                            modifierState =
                                Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 1.dp,
                                        color = DeepBlue,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            textColor = DeepBlue
                        }
                    },
                data = value,
                usePlaceholder = true,
                onValueChanged = { address ->
                    onValueChanged(address)
                }
            )
        }
        Spacer(
            modifier = Modifier
                .width(48.dp)
                .height(2.dp)
                .offset(x = 18.dp, y = 0.dp)
                .background(WhiteGray)
        )
        Text(
            modifier = Modifier
                .offset(x = 20.dp, y = (-12).dp)
                .background(Color.Transparent),
            text = stringResource(id = R.string.address),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = textColor
        )
    }
}

data class Govs(
    val govs: List<String>
)
data class Cities(
    val cities: List<String>
)
fun <T> loadJsonFromRaw(context: Context, rawResId: Int, dtoClass: Class<T>): T? {
    val inputStream = context.resources.openRawResource(rawResId)
    val reader = InputStreamReader(inputStream)
    val jsonString = reader.readText()
    reader.close()

    val gson = Gson()
    return gson.fromJson(jsonString, dtoClass)
}

