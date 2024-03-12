package ncodedev.coffeebase.ui.screens.editcoffee.coffeeimage

import android.Manifest
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CoffeeImageViewModel : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val imageBitMap: MutableState<Bitmap?> = mutableStateOf(null)
    val shouldLaunchGallery = mutableStateOf(false)
    val shouldLaunchCamera = mutableStateOf(false)


    fun dismissDialog() {
        if (visiblePermissionDialogQueue.isNotEmpty()) {
            visiblePermissionDialogQueue.removeFirst()
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    fun launchResultLauncherDependentOnPermission(permission: String) {
        when(permission) {
            Manifest.permission.READ_MEDIA_IMAGES -> shouldLaunchGallery.value = true
            Manifest.permission.READ_EXTERNAL_STORAGE -> shouldLaunchGallery.value = true
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> shouldLaunchGallery.value = true
            Manifest.permission.CAMERA -> shouldLaunchCamera.value = true
        }
    }

    fun imageBitmapSelected(bitmap: Bitmap) {
        imageBitMap.value = bitmap
        shouldLaunchCamera.value = false
        shouldLaunchGallery.value = false
    }
}