package ncodedev.coffeebase.ui.utils
//
//import android.Manifest
//import android.os.Build
//import androidx.activity.compose.ManagedActivityResultLauncher
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.Composable
//import ncodedev.coffeebase.ui.screens.editcoffee.PermissionsViewModel
//
//
//fun requestPermissions(viewModel: PermissionsViewModel): ManagedActivityResultLauncher<String, Boolean> {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) requestMediaPermission()
//    else requestStoragePermission()
//}
//
//private fun requestMediaPermission(viewModel: PermissionsViewModel): ManagedActivityResultLauncher<String, Boolean> {
//    return rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission, onResult = )
//    val storagePermissionResultLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted ->
//            permissionsViewModel.onPermissionResult(
//                permission = Manifest.permission.READ_MEDIA_IMAGES,
//                isGranted = isGranted
//            )
//        }
//    )
//}