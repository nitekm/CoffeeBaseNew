package ncodedev.coffeebase.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.BuildConfig
import ncodedev.coffeebase.data.repository.UserRepository
import ncodedev.coffeebase.model.User
import javax.inject.Inject

sealed interface LoginScreenUiState {
    data class Success(val user: User) : LoginScreenUiState
    object Error: LoginScreenUiState
    object Loading: LoginScreenUiState
}
@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    var loginScreenUiState: LoginScreenUiState by mutableStateOf(LoginScreenUiState.Loading)
        private set

    val request = mutableStateOf<GetCredentialRequest?>(null)

    init {
        createRequestWithGoogleIdOption()
    }

    fun handleSignIn(response: GetCredentialResponse) {
        val credential = response.credential
        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)

       viewModelScope.launch {
           loginScreenUiState = LoginScreenUiState.Loading
           Log.d(TAG, "loading login screen ui state")
           loginScreenUiState = try {
               val user = User(
                   username = googleIdTokenCredential.displayName,
                   email = googleIdTokenCredential.id,
                   pictureUri = googleIdTokenCredential.profilePictureUri.toString(),
                   token = googleIdTokenCredential.idToken
               )
               userRepository.user = user
               Log.d(TAG, "user success: ${user.username}")
               LoginScreenUiState.Success(user)
           } catch (e: Exception) {
               Log.e(TAG, "handleSignIn resulted in exception: $e")
               LoginScreenUiState.Error
           }
       }
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