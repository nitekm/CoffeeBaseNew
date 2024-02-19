package ncodedev.coffeebase.service

import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import ncodedev.coffeebase.BuildConfig

class GoogleAuthService {

    fun signIn() {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(instantiateSignInRequest())
            .build()




    }
    fun instantiateSignInRequest(filterAuthorizedAccounts: Boolean = true): CredentialOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterAuthorizedAccounts)
            .setServerClientId(BuildConfig.CLIENT_ID)
            .build()
    }

}