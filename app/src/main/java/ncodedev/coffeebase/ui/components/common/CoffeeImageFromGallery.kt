package ncodedev.coffeebase.ui.components.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.screens.editcoffee.PermissionsViewModel

@Composable
fun CoffeeImageFromGallery(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val permissionsViewModel = viewModel<PermissionsViewModel>()
    val dialogQueue = permissionsViewModel.visiblePermissionDialogQueue
    val imageBitMap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val showDialog = remember { mutableStateOf(false) }

    val readMediaPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionsViewModel.onPermissionResult(
                permission = Manifest.permission.READ_MEDIA_IMAGES,
                isGranted = isGranted
            )
            if (isGranted) {
                showDialog.value = true
            }
        }
    )

    val readWriteStoragePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach { permission ->
                permissionsViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
                if (perms.values.all { it }) {
                    showDialog.value = true
                }
            }
        }
    )

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                context.contentResolver.openInputStream(it)?.let { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    imageBitMap.value = bitmap
                }
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            imageBitMap.value = it
        }

    ImageChoiceDialog(showDialog) { choice ->
        when (choice) {
            0 -> cameraLauncher.launch(null)
            1 -> galleryLauncher.launch("image/*")
        }
    }
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .size(width = 200.dp, height = 250.dp)
            .clickable {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    readMediaPermissionResultLauncher.launch(
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                } else {
                    readWriteStoragePermissionResultLauncher.launch(
                        listOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ).toTypedArray()
                    )
                }
            },
        painter = rememberAsyncImagePainter(
            model = imageBitMap.value,
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
            onDismiss = permissionsViewModel::dismissDialog,
            onOkClick = {
                permissionsViewModel.dismissDialog()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    readMediaPermissionResultLauncher.launch(
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                } else {
                    readWriteStoragePermissionResultLauncher.launch(
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
            title = { Text("Choose Image From") },
            confirmButton = { },
            dismissButton = { },
            text = {
                Column {
                    TextButton(onClick = { onOptionSelected(0); showDialog.value = false }) {
                        Text(text = "Camera")
                    }
                    TextButton(onClick = { onOptionSelected(1); showDialog.value = false }) {
                        Text(text = "Gallery")
                    }
                }
            }
        )
    }
}