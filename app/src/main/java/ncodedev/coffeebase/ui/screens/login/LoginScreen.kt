package ncodedev.coffeebase.ui.screens.login

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import ncodedev.coffeebase.BuildConfig

@Composable
fun LoginScreen() {
    Surface {
        GoogleSignIn()
    }
}

@Composable
fun GoogleSignIn() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val credentialManager = CredentialManager.create(context)
    var request = createRequestWithGoogleIdOption()

    scope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            handleSignIn(result)
        } catch (e: GetCredentialException) {
            Log.e("LOGIN SCREEN", e.toString())
            try {
                request = createRequestWithGoogleIdOption(false)
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("LOGIN SCREEN", e.toString())
            }
        }
    }
}

private fun handleSignIn(response: GetCredentialResponse) {
    val credential = response.credential
    val googleIdTokenCredential = GoogleIdTokenCredential
        .createFrom(credential.data)

    val googleIdToken = googleIdTokenCredential.idToken
    Log.i("LOGIN SCREEN", "Token aquired: $googleIdToken")
}

//private fun handleFailure(e: GetCredentialException) {
//    if (e.type == TYPE_NO_CREDENTIAL) {
//        createRequestWithGoogleIdOption(false)
//        try {
//            val result = credentialManager.getCredential(
//                request = request,
//                context = context
//            )
//            handleSignIn(result)
//        } catch (e: GetCredentialException) {
//            Log.e("LOGIN SCREEN", e.toString())
//            handleFailure(e)
//        }
//    }
//}

private fun createRequestWithGoogleIdOption(filterAuthorizedAccounts: Boolean = true): GetCredentialRequest {
    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(BuildConfig.CLIENT_ID)
        .build()

    return GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}