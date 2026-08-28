package com.sanadedu.parent.feed.domain.util

import android.graphics.ImageFormat
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import android.util.Log
import androidx.annotation.OptIn
import com.sanadedu.parent.feed.data.qr_data.BarcodeResult

class QrCodeMLAnalyzer(
    private val onQrCodeScanned: (BarcodeResult) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
        )
        .build()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        if (imageProxy.format != ImageFormat.YUV_420_888) {
            Log.e("QrCodeAnalyzerGms", "Unsupported imageProxy format: ${imageProxy.format}")
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val valueType = barcode.valueType
                        if (valueType == Barcode.TYPE_TEXT) {
                            onQrCodeScanned(
                                BarcodeResult(
                                    result = barcode.rawValue ?: "",
                                    boundingBox = barcode.boundingBox,
                                    lastFrame = imageProxy.toBitmap()
                                )
                            )
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QrCodeAnalyzerGms", "QR Code scan failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
