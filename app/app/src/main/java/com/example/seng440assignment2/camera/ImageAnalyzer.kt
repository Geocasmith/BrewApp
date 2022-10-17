package com.example.seng440assignment2.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.seng440assignment2.MainViewModel
import com.example.seng440assignment2.R
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(private val onBarcodeScanned: (List<Barcode>?) -> Unit ): ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { onBarcodeScanned(it) }
                .addOnCompleteListener { imageProxy.close() }
        }
    }
}

@Composable
fun ScanScreen(viewModel: MainViewModel, onFoundBarcode: (String) -> Unit, onNotFoundBarcode: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

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
        onResult = { granted -> hasCamPermission = granted }
    )

    LaunchedEffect(key1 = true) {
        // Check that camera permission has been given
        launcher.launch(Manifest.permission.CAMERA)
    }

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(hasCamPermission) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AndroidView(
                    factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = Preview.Builder().build()
                        var gettingBeer = false

                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)

                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            BarcodeAnalyzer { result ->
                                if (!gettingBeer && !result.isNullOrEmpty()) {
                                    val barcode = result[0].rawValue
                                    if (!barcode.isNullOrEmpty()) {
                                        gettingBeer = true
                                        val request = viewModel.getObjectRequest(context, "beer/${barcode}", {
                                            onFoundBarcode(barcode.toString())
                                            gettingBeer = false
                                        }, { error ->
                                            when (error.networkResponse.statusCode) {
                                                404 -> {
                                                    onNotFoundBarcode(barcode.toString())
                                                    gettingBeer = false
                                                }
                                                else -> Toast.makeText(context, context.resources.getText(R.string.generic_500_error), Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                        viewModel.addRequestToQueue(request)
                                    }
                                }
                            })
                        try {
                            cameraProviderFuture.get().bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

    }
}