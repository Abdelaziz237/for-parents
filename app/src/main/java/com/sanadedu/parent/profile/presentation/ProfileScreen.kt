package com.sanadedu.parent.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.BasicTextField
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.ui.TryAgainScreen
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.profile.presentation.info.ProfileEvent
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.MidNightBlueLight
import com.sanadedu.parent.theme.bl

@Composable
fun ProfileScreen(
    onBackPressed: () -> Unit,
    restartTheApp: () -> Unit,
    viewModel: ProfileViewModel = viewModel(
        modelClass = ProfileViewModel::class.java,
        factory = ProfileViewModel.ProfileViewModelFactory(
            restartTheApp = restartTheApp
        )
    )
) {
    var isDeleteAccountDialogActive by remember {
        mutableStateOf(false)
    }

    var isLogoutDialogActive by remember {
        mutableStateOf(false)
    }

    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvent.StartLoading)
        viewModel.onEvent(ProfileEvent.GetProfileInfo)
    }

    Box(
        modifier = Modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .alpha(0.6f),
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_header),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            BasicAppBar(text = stringResource(id = R.string.profile), navigateUp = onBackPressed)
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = bl,
                    trackColor = LightGray,
                    strokeCap = StrokeCap.Round
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.1f))

                    Image(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.tutor_4),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = state.username,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Text(
                        text = state.parentCode,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(1.dp, shape = RoundedCornerShape(16.dp))
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        LazyColumn {
                            item {
                                ProfileItem(
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_profile),
                                    text = stringResource(id = R.string.students),
                                    info = stringResource(id = R.string.students_count) + state.studentsCount.toString()
                                )
                            }
                            item {
                                ProfileItem(
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_phone),
                                    text = stringResource(id = R.string.phone_number),
                                    info = state.phoneNumber
                                )
                            }
                            item {
                                ProfileItem(
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                                    text = stringResource(id = R.string.logout),
                                    info = stringResource(id = R.string.logout_info),
                                    onClick = {
                                        isLogoutDialogActive = true
                                    }
                                )
                            }
                            item {
                                ProfileItem(
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_delete_account),
                                    text = stringResource(id = R.string.delete_account),
                                    info = stringResource(id = R.string.delete_account_info),
                                    onClick = {
                                        isDeleteAccountDialogActive = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isDeleteAccountDialogActive) {
            DeleteAccountDialog(
                onDismiss = {
                    isDeleteAccountDialogActive = false
                },
                onDeleteAccount = {
                    viewModel.onEvent(ProfileEvent.DeleteAccount)
                }
            )
        }

        if (isLogoutDialogActive) {
            LogoutDialog(
                onDismiss = {
                    isLogoutDialogActive = false
                },
                onLogout = {
                    viewModel.onEvent(ProfileEvent.Logout)
                }
            )
        }

        if (state.showTryAgain) TryAgainScreen(
            onTryAgain = {
                viewModel.onEvent(event = ProfileEvent.StartLoading)
                viewModel.onEvent(event = ProfileEvent.GetProfileInfo)
            }
        )
    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    text: String,
    info: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.bg_ellipse),
                contentDescription = null
            )
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = icon,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = info,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_forward),
            contentDescription = null
        )
    }
}

@Composable
fun DeleteAccountDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    val validationText = stringResource(id = R.string.validation_string)
    var inputText by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MidNightBlueLight.copy(alpha = 0.15f),
                                shape = CircleShape
                            )
                            .clickable {
                                onDismiss()
                            }
                            .padding(8.dp)
                            .size(18.dp)
                            .align(Alignment.End),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_black),
                        contentDescription = stringResource(id = R.string.close),
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.delete_account_instructions),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MidNightBlue,
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.delete_account_instructions_2),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MidNightBlueLight,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.delete_account_instructions_3) + "\n" + stringResource(
                            id = R.string.validation_string
                        ),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MidNightBlueLight,
                        fontSize = 14.sp,
                    )

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = stringResource(id = R.string.validate),
                        onValueChanged = { value ->
                            inputText = value
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorRed
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = inputText == validationText,
                        onClick = { onDeleteAccount() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = ErrorRed
                        ),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { onDismiss() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = ErrorRed
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onLogout: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MidNightBlueLight.copy(alpha = 0.15f),
                                shape = CircleShape
                            )
                            .clickable {
                                onDismiss()
                            }
                            .padding(8.dp)
                            .size(18.dp)
                            .align(Alignment.End),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_black),
                        contentDescription = stringResource(id = R.string.close),
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.logout_instructions),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MidNightBlue,
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.logout_instructions_2),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MidNightBlueLight,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorRed
                        ),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { onLogout() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.logout),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = ErrorRed
                        ),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { onDismiss() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = ErrorRed
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}