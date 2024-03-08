package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel() {
    val visiblePermissionDialogQueue = mutableListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted) {
            visiblePermissionDialogQueue.add(0, permission)
        }
    }
}