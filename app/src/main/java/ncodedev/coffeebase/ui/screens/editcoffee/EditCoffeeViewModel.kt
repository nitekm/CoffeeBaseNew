package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ncodedev.coffeebase.model.enums.RoastProfile

class EditCoffeeViewModel : ViewModel() {

    var coffeeName = mutableStateOf("")
    var isNameValid = mutableStateOf(true)
    var origin = mutableStateOf("")
    var roaster = mutableStateOf("")
    var processing = mutableStateOf("")
    var roastProfile = mutableStateOf(RoastProfile.ROAST_PROFILE)
    var region = mutableStateOf("")
    var continent = mutableStateOf("")
    var farm = mutableStateOf("")
    var cropHeight = mutableIntStateOf(0)
    var scaRating = mutableIntStateOf(0)
    var rating = mutableDoubleStateOf(0.0)
    //TODO tags

    fun saveCoffee() {

    }

    fun validateAndSetCoffeeName(coffeeName: String) {
        this.coffeeName.value = coffeeName
        isNameValid.value = coffeeName.isNotEmpty()
    }
}