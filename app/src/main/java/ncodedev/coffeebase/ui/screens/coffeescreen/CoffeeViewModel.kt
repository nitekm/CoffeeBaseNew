package ncodedev.coffeebase.ui.screens.coffeescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import javax.inject.Inject

@HiltViewModel
class CoffeeViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {
    val coffee = mutableStateOf<Coffee?>(null)

}