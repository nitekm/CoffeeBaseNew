package ncodedev.coffeebase.ui.screens.login

import android.content.Context
import android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL
import android.credentials.GetCredentialException.TYPE_USER_CANCELED
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.User
import ncodedev.coffeebase.ui.components.Screens

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    var user: User? by remember { mutableStateOf(null) }

    user =  when (val uiState = viewModel.loginScreenUiState) {
        is LoginScreenUiState.Success -> uiState.user
        else -> {
            Log.e("Login Screen", "User nullified")
            null
        }
    }


    val request by viewModel.request
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    val scope = rememberCoroutineScope()


    //TODO: add TopBar with title here!
    Surface {
        LaunchedEffect(request) {
            request?.let {
                scope.launch {
                    try {
                        val result = credentialManager.getCredential(
                            request = it,
                            context = context
                        )
                        viewModel.handleSignIn(result)
                    } catch (e: GetCredentialException) {
                        handleLoginError(e, context, viewModel)
                    }
                }
            }
        }
        LaunchedEffect(user) {
            user?.let {
                Log.d("inside user launched effect", "current user: ${user?.username}")
                navController.navigate(Screens.MyCoffeeBase.name) {
                    popUpTo(Screens.Login.name) {
                        inclusive = true
                    }
                }
            }
        }
    }
}


private fun handleLoginError(
    e: GetCredentialException,
    context: Context,
    viewModel: LoginViewModel
) {
    when (e.type) {
        TYPE_NO_CREDENTIAL -> {
            Log.e("LoginScreen", e.message.toString())
            Toast.makeText(
                context,
                context.getString(R.string.please_log_in_on_the_device),
                Toast.LENGTH_LONG
            ).show()
            viewModel.createRequestSignInWithGoogleOption()
        }

        TYPE_USER_CANCELED -> {
            Log.e("LoginScreen", e.message.toString())
            Toast.makeText(
                context,
                context.getString(R.string.please_log_in_before_proceeding),
                Toast.LENGTH_LONG
            ).show()
            viewModel.createRequestWithGoogleIdOption()
        }

        else -> {
            Log.e("LoginScreen", e.message.toString())
            Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_LONG).show()
        }
    }
}