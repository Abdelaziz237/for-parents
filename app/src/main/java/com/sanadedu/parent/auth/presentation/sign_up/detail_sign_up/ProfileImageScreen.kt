package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.presentation.CustomProgressBar
import com.sanadedu.parent.auth.presentation.LabelText
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.info.RegisterFormEvent
import com.sanadedu.parent.core.domain.usecases.ShowToast
import com.sanadedu.parent.core.presentation.components.MainButton
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.CautionYellow
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.HintTextColor
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.MidNightBlueLight
import com.sanadedu.parent.theme.TextColor
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileImageScreen(
    viewModel: DetailSignUpViewModel,
    onSubmit: () -> Unit
) {
    val formState = viewModel.formState.value

    val context = LocalContext.current

    val permissions = mutableListOf<String>().apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        }
    }.toTypedArray()

    var allPermissionsGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        allPermissionsGranted = permissionsMap.values.all { it }
    }

    var isEnabled by remember {
        mutableStateOf(true)
    }

    var isStorageDialogShown by remember {
        mutableStateOf(false)
    }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val id = formState.email.split('@')[0]
            if (allPermissionsGranted) {
                isEnabled = false

                viewModel.onEvent(
                    event = RegisterFormEvent
                        .UploadImage(
                            email = formState.email,
                            imageFile = generateImageMediaFile(
                                applicationContext = context,
                                imageUri = uri,
                                fileName = "$id@${System.currentTimeMillis()}"
                            ),
                            mimeType = (getMimeType(context, uri) ?: "").toLowerCase(Locale.current)
                        ),
                    callback = { isEnabled = true
                    }
                )
            }
        } else {
            ShowToast().invoke(
                message = R.string.no_media_selected,
                type = ToastType.ErrorToast
            )
        }
    }

    LaunchedEffect(Unit) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            allPermissionsGranted = true
        }
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
                    percent = 0.98f
                )

                LabelText(
                    modifier = Modifier.padding(top = 48.dp),
                    text = R.string.profile_question_header
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp)
                        .height(200.dp)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .clickable {
                            if (allPermissionsGranted) {
                                pickMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            } else {
                                if (permissions.all {
                                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                                    }) {
                                    allPermissionsGranted = true
                                } else {
                                    isStorageDialogShown = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (formState.profileImage == "") {
                        ImageContainer(
                            modifier = Modifier
                        )
                    } else {  }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(start = 16.dp), color = LightGray)
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = stringResource(id = R.string.or),
                        color = TextColor,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                    Divider(
                        Modifier
                            .weight(1f)
                            .padding(end = 16.dp), color = LightGray
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .padding(top = 20.dp, bottom = 20.dp)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(16.dp),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CameraContainer()
                }
            }

            if (isStorageDialogShown) {
                StorageAlertDialog(
                    onDismiss = {
                        isStorageDialogShown = false
                    },
                    askPermissions = {
                        permissionLauncher.launch(permissions)
                    }
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            MainButton(
                modifier = Modifier,
                text = stringResource(id = R.string.next),
                isEnable = isEnabled,
                onClick = {
                    onSubmit()
                }
            )
        }
    }
}

@Composable
fun ImageContainer(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_image),
            contentDescription = stringResource(id = R.string.image_icon))
        Text(
            text = stringResource(id = R.string.select_image),
            color = HintTextColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CameraContainer() {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
            contentDescription = stringResource(id = R.string.camera_icon))
        Text(
            text = stringResource(id = R.string.take_photo),
            color = HintTextColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

private fun generateImageMediaFile(
    applicationContext: Context,
    imageUri: Uri,
    fileName: String
): File {
    val imageFile = File(applicationContext.cacheDir, fileName)
    val outputStream = FileOutputStream(imageFile)

    val resolver = applicationContext.contentResolver
    resolver.openInputStream(imageUri).use { stream ->
        stream?.copyTo(outputStream)
    }

    return imageFile
}

fun getMimeType(applicationContext: Context, uri: Uri): String? {
    val mimeType: String?
    if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        val contentResolver = applicationContext.contentResolver
        mimeType = contentResolver.getType(uri)
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.lowercase()
        )
    }
    return mimeType
}


@Composable
private fun StorageAlertDialog(
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
                    text = stringResource(id = R.string.storage_permission_needed),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MidNightBlue,
                    fontSize = 20.sp
                )

                Text(
                    text = stringResource(id = R.string.why_storage_needed),
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