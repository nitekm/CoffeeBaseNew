package ncodedev.coffeebase.ui.utils

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ncodedev.coffeebase.ui.screens.editcoffee.CoffeeImageViewModel

@Composable
fun readMediaPermissionResultLauncher(
    coffeeImageViewModel: CoffeeImageViewModel
): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            coffeeImageViewModel.onPermissionResult(
                permission = Manifest.permission.READ_MEDIA_IMAGES,
                isGranted = isGranted
            )
            if (isGranted) {
                coffeeImageViewModel.launchResultLauncherDependentOnPermission(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    )
}

@Composable
fun readWriteStoragePermissionResultLauncher(
    coffeeImageViewModel: CoffeeImageViewModel
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach { permission ->
                coffeeImageViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
                if (perms.values.all { it }) {
                    coffeeImageViewModel.launchResultLauncherDependentOnPermission(permission)
                }
            }
        }
    )
}

@Composable
fun cameraPermissionResultLauncher(
    coffeeImageViewModel: CoffeeImageViewModel
): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            coffeeImageViewModel.onPermissionResult(
                Manifest.permission.CAMERA,
                isGranted = isGranted
            )
            if (isGranted) {
                coffeeImageViewModel.launchResultLauncherDependentOnPermission(Manifest.permission.CAMERA)
            }
        }
    )
}

@Composable
fun galleryLauncher(
    coffeeImageViewModel: CoffeeImageViewModel
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.let { stream ->
                val bitmap = BitmapFactory.decodeStream(stream)
                coffeeImageViewModel.imageBitmapSelected(bitmap)
            }
        }
    }
}

@Composable
fun cameraLauncher(
    coffeeImageViewModel: CoffeeImageViewModel
): ManagedActivityResultLauncher<Void?, Bitmap?> {
    return rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            coffeeImageViewModel.imageBitmapSelected(it)
        }
    }
}