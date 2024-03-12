package ncodedev.coffeebase.ui.screens.editcoffee.coffeeimage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.common.PermissionDialog
import ncodedev.coffeebase.ui.components.common.StoragePermissionTextProvider
import ncodedev.coffeebase.ui.utils.*

@Composable
fun CoffeeImageFromGallery(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coffeeImageViewModel = viewModel<CoffeeImageViewModel>()
    val dialogQueue = coffeeImageViewModel.visiblePermissionDialogQueue

    val showGalleryOrCameraDialog = remember { mutableStateOf(false) }

    val readMediaPermissionResult =
        readMediaPermissionResultLauncher(coffeeImageViewModel = coffeeImageViewModel)
    val readWriteStoragePermissionResult =
        readWriteStoragePermissionResultLauncher(coffeeImageViewModel = coffeeImageViewModel)
    val cameraPermissionResult =
        cameraPermissionResultLauncher(coffeeImageViewModel = coffeeImageViewModel)

    val galleryResult =
        galleryLauncher(coffeeImageViewModel = coffeeImageViewModel)
    val cameraResult =
        cameraLauncher(coffeeImageViewModel = coffeeImageViewModel)

    ImageChoiceDialog(showGalleryOrCameraDialog) { choice ->
        when (choice) {
            0 -> {
                coffeeImageViewModel.dismissDialog()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    readMediaPermissionResult.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    readWriteStoragePermissionResult.launch(
                        listOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ).toTypedArray()
                    )
                }
            }

            1 -> {
                coffeeImageViewModel.dismissDialog()
                cameraPermissionResult.launch(Manifest.permission.CAMERA)
            }
        }
    }
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .size(width = 165.dp, height = 210.dp)
            .clickable {
                showGalleryOrCameraDialog.value = true
            },
        painter = rememberAsyncImagePainter(
            model = coffeeImageViewModel.imageBitMap.value,
            error = painterResource(R.drawable.coffeebean),
        ),
        contentDescription = stringResource(R.string.coffee_photo),
        contentScale = ContentScale.Crop,
    )
    dialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = StoragePermissionTextProvider(),
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                LocalContext.current as Activity, permission
            ),
            onDismiss = coffeeImageViewModel::dismissDialog,
            onOkClick = {
                coffeeImageViewModel.dismissDialog()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    readMediaPermissionResult.launch(
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                } else {
                    readWriteStoragePermissionResult.launch(
                        listOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ).toTypedArray()
                    )
                }
            },
            onGoToSettings = { context.openAppSettings() }
        )
    }
    if (coffeeImageViewModel.shouldLaunchGallery.value) {
        galleryResult.launch("image/*")
    }
    if (coffeeImageViewModel.shouldLaunchCamera.value) {
        cameraResult.launch()
    }
}


fun Context.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

@Composable
fun ImageChoiceDialog(showDialog: MutableState<Boolean>, onOptionSelected: (Int) -> Unit) {
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { showDialog.value = false },
            title = { Text(
                text = stringResource(R.string.select_image_from),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
            ) },
            confirmButton = { },
            dismissButton = { },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.add_photo_gallery),
                            contentDescription = stringResource(R.string.gallery),
                            modifier = Modifier.size(65.dp)
                        )
                        Button(onClick = {
                            onOptionSelected(0)
                            showDialog.value = false
                        }) {
                            Text(
                                text = stringResource(R.string.gallery),
                                fontSize = 15.sp
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.add_photo_camera),
                            contentDescription = stringResource(R.string.gallery),
                            modifier = Modifier.size(65.dp)
                        )
                        Button(onClick = {
                            onOptionSelected(1)
                            showDialog.value = false
                        }) {
                            Text(
                                text = stringResource(R.string.camera),
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        )
    }
}