package com.sanadedu.parent.core.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sanadedu.parent.R
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.data.Screen
import com.sanadedu.parent.core.domain.navigation.MainGraph
import com.sanadedu.parent.core.presentation.components.BottomAppBar
import com.sanadedu.parent.feed.presentation.add_student.CamAlertDialog
import com.sanadedu.parent.feed.presentation.add_student.ParentKeyDialog
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.MidNightBlueLight

@Composable
fun MainScreen(
    mainNavController: NavHostController = rememberNavController(),
    restartTheApp: () -> Unit
) {
    var isAddStudentDialogActive by remember {
        mutableStateOf(false)
    }

    var isCamDialogShown by remember {
        mutableStateOf(false)
    }

    var isLogoutDialogActive by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Log.d("msg", "CAM ACCESS GRANTED")
                hasCamPermission = true
                isCamDialogShown = false
                isAddStudentDialogActive = true
            } else {
                ActivityResultContracts.RequestPermission()
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                MainGraph( mainNavController = mainNavController )
            }
            BottomAppBar(
                navController = mainNavController,
                onHomePressed = {
                    mainNavController.navigate(Screen.MainScreen.HomeScreen.route) {
                        popUpTo(Screen.MainScreen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onProfilePressed = {
//                    isLogoutDialogActive = true
                    mainNavController.navigate(Screen.MainScreen.ProfileScreen.route)
                },
                onAddStudentPressed = {
                    if (hasCamPermission) isAddStudentDialogActive = true
                    else isCamDialogShown = true
                },
            )
        }

        if (isAddStudentDialogActive){
            ParentKeyDialog(
                hasCamPermission = hasCamPermission,
                showCamDialog = {
                    isCamDialogShown = true
                    isAddStudentDialogActive = false
                },
                onAdded = {
                    isAddStudentDialogActive = false
                },
                onDismiss = {
                    isAddStudentDialogActive = false
                }
            )
        }

        if (isCamDialogShown) {
            CamAlertDialog(
                onDismiss = {
                    isCamDialogShown = false
                    isAddStudentDialogActive = true
                },
                askPermissions = {
                    launcher.launch(Manifest.permission.CAMERA)
                }
            )
        }

        if (isLogoutDialogActive) {
            LogoutDialog(
                onDismiss = {
                    isLogoutDialogActive = false
                },
                onLogout = {
                    LocalClient.getClientViewModel().logout(
                        callback = {
                            restartTheApp()
                        }
                    )
                }
            )
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
                        modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.fillMaxWidth()
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