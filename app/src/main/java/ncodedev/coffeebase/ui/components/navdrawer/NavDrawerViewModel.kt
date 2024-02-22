package ncodedev.coffeebase.ui.components.navdrawer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ncodedev.coffeebase.data.repository.UserRepository
import ncodedev.coffeebase.model.User
import javax.inject.Inject

@HiltViewModel
class NavDrawerViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val user: User? get() = userRepository.user
}