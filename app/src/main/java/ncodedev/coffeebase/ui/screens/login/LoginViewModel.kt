package ncodedev.coffeebase.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ncodedev.coffeebase.BuildConfig
import ncodedev.coffeebase.model.User
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()
//    private val _user = mutableStateOf<User?>(null)
//    val user: State<User?> = _user
    val request = mutableStateOf<GetCredentialRequest?>(null)

    init {
        createRequestWithGoogleIdOption()
    }

    fun handleSignIn(response: GetCredentialResponse) {
        val credential = response.credential
        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)

        _user.value = User(
            username = googleIdTokenCredential.displayName,
            pictureUri = googleIdTokenCredential.profilePictureUri.toString(),
            email = googleIdTokenCredential.id,
            token = googleIdTokenCredential.idToken
        )

    }


    fun createRequestWithGoogleIdOption() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(BuildConfig.CLIENT_ID)
            .build()

        request.value = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun createRequestSignInWithGoogleOption() {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(BuildConfig.CLIENT_ID)
            .build()

        request.value = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
    }
}