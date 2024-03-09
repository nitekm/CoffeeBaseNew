package ncodedev.coffeebase.ui.utils

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import ncodedev.coffeebase.ui.screens.editcoffee.PermissionsViewModel

@Composable
fun ReadMediaPermissionResultLauncher(permissionsViewModel: PermissionsViewModel): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
            permissionsViewModel.onPermissionResult(
                permission = Manifest.permission.READ_MEDIA_IMAGES,
                isGranted = isGranted
            )
            if (isGranted) {
                showDialog.value = true
            }
    })
}
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