package com.dailyhealthcoach.barcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.compose.ui.viewinterop.AndroidView
import com.dailyhealthcoach.ui.theme.CyanAccent
import com.dailyhealthcoach.ui.theme.MutedText
import com.dailyhealthcoach.ui.theme.PositiveAccent
import com.dailyhealthcoach.ui.theme.PrimaryText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun NutritionLabelScannerScreen(
    onTextRecognized: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    @Suppress("DEPRECATION")
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { hasCameraPermission = it }
    LaunchedEffect(Unit) { if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA) }

    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var capturedSections by remember { mutableStateOf(listOf<String>()) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    val sectionCount = capturedSections.size
    val sectionInstruction = when (sectionCount) {
        0 -> "Start with the top of the label"
        1 -> "Now capture the middle section"
        else -> "Capture any remaining sections, or tap Finish"
    }

    fun captureSection() {
        if (isProcessing) return
        isProcessing = true
        errorMessage = null
        val executor = ContextCompat.getMainExecutor(context)
        imageCapture.takePicture(
            executor,
            object : ImageCapture.OnImageCapturedCallback() {
                @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
                override fun onCaptureSuccess(image: ImageProxy) {
                    val mediaImage = image.image
                    if (mediaImage != null) {
                        val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                            .process(inputImage)
                            .addOnSuccessListener { result ->
                                val text = result.textBlocks.joinToString("\n") { block ->
                                    block.lines.joinToString("\n") { it.text }
                                }
                                capturedSections = capturedSections + text
                                isProcessing = false
                            }
                            .addOnFailureListener {
                                isProcessing = false
                                errorMessage = "Could not read this section. Try again with better lighting."
                            }
                            .addOnCompleteListener { image.close() }
                    } else {
                        isProcessing = false
                        errorMessage = "Capture failed. Try again."
                        image.close()
                    }
                }

                override fun onError(exc: ImageCaptureException) {
                    isProcessing = false
                    errorMessage = "Capture failed. Try again."
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (hasCameraPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val executor = ContextCompat.getMainExecutor(ctx)
                    val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                    val future = ProcessCameraProvider.getInstance(ctx)
                    future.addListener({
                        runCatching {
                            val provider = future.get()
                            provider.unbindAll()
                            provider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)
                        }
                    }, executor)
                    previewView
                }
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.72f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Curved-label guidance — always visible
                Text(
                    "For curved bottles, capture the label in 2–3 sections",
                    color = MutedText,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    if (isProcessing) "Reading section..." else sectionInstruction,
                    color = PrimaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                if (sectionCount > 0) {
                    Text(
                        "$sectionCount section${if (sectionCount == 1) "" else "s"} captured",
                        color = PositiveAccent,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                errorMessage?.let {
                    Text(it, color = Color(0xFFFF6B6B), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                }

                if (sectionCount == 0) {
                    // First capture
                    Button(
                        onClick = { captureSection() },
                        enabled = !isProcessing,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                    ) {
                        Text(
                            if (isProcessing) "Processing..." else "Capture Label",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    // Subsequent captures — two-button row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onTextRecognized(capturedSections.joinToString("\n")) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(999.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.7f))
                        ) {
                            Text("Finish Scan", color = CyanAccent, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = { captureSection() },
                            enabled = !isProcessing,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(999.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                        ) {
                            Text(
                                if (isProcessing) "Reading..." else "Capture Another",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Camera permission is required to scan labels.",
                    color = PrimaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }

        TextButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().padding(8.dp)
        ) {
            Text("← Back", color = CyanAccent, fontWeight = FontWeight.Bold)
        }
    }
}
