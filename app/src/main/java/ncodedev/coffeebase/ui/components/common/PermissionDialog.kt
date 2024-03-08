package ncodedev.coffeebase.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.R

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider()
                Text(
                    text =  if(isPermanentlyDeclined) stringResource(R.string.grant_permission)
                    else stringResource(R.string.ok),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) onGoToSettings()
                            else onOkClick()
                        }
                        .padding(16.dp)
                )
            }
        },
        title = { 
                Text(text = stringResource(R.string.permission_required))
        },
        text = { Text(text = permissionTextProvider.getDescription(isPermanentlyDeclined = isPermanentlyDeclined))},
        modifier = modifier

    )
}

interface PermissionTextProvider {
    @Composable
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class StoragePermissionTextProvider: PermissionTextProvider {
    @Composable
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) stringResource(R.string.storage_permission_required_go_to_settings)
         else stringResource(R.string.storage_permission_required)
    }
}