package com.sanadedu.parent.feed.presentation.add_student

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.BasicTextField
import com.sanadedu.parent.core.presentation.ui.ParentKeyDialogEvents
import com.sanadedu.parent.core.presentation.ui.ParentKeyDialogViewModel
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.feed.presentation.QrScannerScreen
import com.sanadedu.parent.theme.CautionYellow
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.MidNightBlueLight
import com.sanadedu.parent.theme.bl

@Composable
fun ParentKeyDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onAdded:() -> Unit,
    hasCamPermission: Boolean,
    showCamDialog: () -> Unit,
    viewModel: ParentKeyDialogViewModel = viewModel()
) {
    val state = viewModel.state.value

    var isScannerModeActivated by remember {
        mutableStateOf(false)
    }

    var parentKey by remember {
        mutableStateOf("")
    }

    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            if (isScannerModeActivated) {
                QrScannerScreen(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(480.dp),
                    onCodeScanned = { capturedVal ->
                        parentKey = capturedVal
                        isScannerModeActivated = false
                    },
                    dismissScanner = { isScannerModeActivated = false }
                )
            } else {
                Box(modifier = Modifier) {
                    if (state.isLoading) {
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
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
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
                            text = stringResource(id = R.string.add_student),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = MidNightBlue,
                            fontSize = 20.sp
                        )

                        Text(
                            text = stringResource(id = R.string.add_student_instructions),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            color = MidNightBlueLight,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                modifier = Modifier.weight(1f),
                                label = stringResource(R.string.parent_key),
                                initValue = parentKey,
                            ) { newVal ->
                                parentKey = newVal
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Box(modifier = Modifier
                                .padding(top = 6.dp)
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(color = MidNightBlueLight, shape = CircleShape)
                                .clickable {
                                    if (hasCamPermission) isScannerModeActivated = true
                                    else {
                                        showCamDialog()
                                    }
                                },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_scan),
                                    contentDescription = stringResource(id = R.string.scan)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepBlue
                            ),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                if (parentKey.isNotEmpty()) {
                                    viewModel.onEvent(ParentKeyDialogEvents.StartLoading)
                                    viewModel.onEvent(
                                        ParentKeyDialogEvents.AddStudentByKey(
                                            parentKey
                                        ),
                                        callback = {
                                            onAdded()
                                        }
                                    )
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.confirm),
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CamAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    askPermissions: () -> Unit
) {
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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

                Box(modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        color = CautionYellow.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .background(
                                color = CautionYellow,
                                shape = CircleShape
                            )
                            .padding(10.dp)
                            .size(24.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_warning),
                        contentDescription = stringResource(id = R.string.success),
                    )
                }


                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.permission_needed),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MidNightBlue,
                    fontSize = 20.sp
                )

                Text(
                    text = stringResource(id = R.string.why_cam_needed),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MidNightBlueLight,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBlue
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        askPermissions()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.ask_permission),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
