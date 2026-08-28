package com.sanadedu.parent.feed.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.feed.data.qr_data.QrState
import com.sanadedu.parent.feed.domain.util.QrCodeMLAnalyzer


@Composable
fun QrScannerScreen(
    modifier: Modifier = Modifier,
    defaultState: QrState = QrState(),
    onCodeScanned: (String) -> Unit,
    dismissScanner: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    var state by remember { mutableStateOf(defaultState) }

    LaunchedEffect(key1 = true) {
        checkModuleAvailability(
            context = context,
            onModulesAvailable = {
                state = state.copy(isModuleInstalled = true, isLoading = false)
            },
            onModulesNotAvailable = {
                installMLKitScanner(
                    context = context,
                    onSuccess = {
                        Log.d("QrScannerScreen", "MLKit module installed successfully")
                        state = state.copy(isModuleInstalled = true)
                    },
                    onFailure = {
                        state = state.copy(isModuleInstalled = false)
                    }
                )
                state = state.copy(isLoading = false)
            }
        )
    }

    DisposableEffect(key1 = cameraProviderFuture) {
        onDispose {
            cameraProviderFuture.get().unbindAll()
        }
    }

    if (!state.isLoading) {
        if (state.isModuleInstalled) {
            Box(modifier = modifier) {
                if (state.isCameraFrozen) {
                    state.imageBitmap?.let { bitmap ->
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            bitmap = bitmap.rotate(90f).asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(0.3f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = Color.Black)
                        )
                    }
                } else {
                    CameraView(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(16.dp)
                            ),
                        state = state,
                        cameraProviderFuture = cameraProviderFuture,
                        lifecycleOwner = lifecycleOwner,
                        onQrCaptured = { updatedState ->
                            state = updatedState
                            onCodeScanned(updatedState.code)
                        },
                        dismissScanner = {
                            dismissScanner()
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Camera permission is required to scan QR codes.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        }
    } else Box(modifier = modifier)
}

@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    state: QrState,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    onQrCaptured: (QrState) -> Unit,
    dismissScanner: () -> Unit
) {
    var isFlashActive by remember {
        mutableStateOf(false)
    }

    var cameraControl: CameraControl? by remember { mutableStateOf(null) }

    Box(
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeMLAnalyzer { barcodeResult ->
                        onQrCaptured(
                            state.copy(
                                code = barcodeResult.result,
                                boundedBox = barcodeResult.boundingBox,
                                imageBitmap = barcodeResult.lastFrame,
                                isCameraFrozen = true
                            )
                        )
                    }
                )
                try {
                    val camera = cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                    cameraControl = camera.cameraControl
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                previewView
            },
            modifier = Modifier
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.Black)
        )

        Row {
            Box(modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .clickable {
                    dismissScanner()
                },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_white_arrow),
                    contentDescription = stringResource(id = R.string.flash)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .clickable {
                    isFlashActive = !isFlashActive
                    cameraControl?.enableTorch(isFlashActive)
                },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = if (isFlashActive) painterResource(id = R.drawable.ic_active_thunder) else painterResource(id = R.drawable.ic_thunder),
                    contentDescription = stringResource(id = R.string.flash)
                )
            }
        }
    }

}

private fun checkModuleAvailability(
    context: Context,
    onModulesAvailable: () -> Unit,
    onModulesNotAvailable: () -> Unit
) {
    val moduleInstallClient = ModuleInstall.getClient(context)
    val optionalModuleApi = BarcodeScanning.getClient()
    moduleInstallClient
        .areModulesAvailable(optionalModuleApi)
        .addOnSuccessListener {
            if (it.areModulesAvailable()) {
                onModulesAvailable()
            } else {
                Log.d("QrScannerScreen", "MLKit module not available")
                onModulesNotAvailable()
            }
        }
        .addOnFailureListener {
            Log.e("QrScannerScreen", "Failed to check MLKit module availability", it)
        }
}

private fun installMLKitScanner(
    context: Context,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val moduleInstallClient = ModuleInstall.getClient(context)
    val optionalModuleApi = BarcodeScanning.getClient()
    val moduleInstallRequest = ModuleInstallRequest.newBuilder()
        .addApi(optionalModuleApi)
        .build()

    moduleInstallClient
        .installModules(moduleInstallRequest)
        .addOnSuccessListener {
            if (it.areModulesAlreadyInstalled()) {
                onSuccess()
            }
        }
        .addOnFailureListener {
            onFailure()
        }

}

private fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}