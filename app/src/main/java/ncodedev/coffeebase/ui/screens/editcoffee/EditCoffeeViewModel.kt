package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.model.enums.RoastProfile

class EditCoffeeViewModel : ViewModel() {

    var coffeeName = mutableStateOf("")
    var isNameValid = mutableStateOf(true)
    var origin = mutableStateOf("")
    var roaster = mutableStateOf("")
    var processing = mutableStateOf("")
    var roastProfile = mutableStateOf(RoastProfile.ROAST_PROFILE)
    var region = mutableStateOf("")
    var continent = mutableStateOf(Continent.CONTINENT)
    var farm = mutableStateOf("")
    var cropHeight = mutableStateOf("")
    var isCropHeightValid = mutableStateOf(true)
    var scaRating = mutableStateOf("")
    val isScaRatingValid = mutableStateOf(true)
    var rating = mutableDoubleStateOf(0.0)
    //TODO tags

    fun saveCoffee() {

    }

    fun validateAndSetCoffeeName(coffeeName: String) {
        this.coffeeName.value = coffeeName
        isNameValid.value = coffeeName.isNotEmpty()
    }

    fun validateAndSetCropHeight(cropHeight: Int) {
        this.cropHeight.value = cropHeight.toString()
        isCropHeightValid.value = cropHeight in 0..8849
    }

    fun validateAndSetScaRating(scaRating: Int) {
        this.scaRating.value = scaRating.toString()
        isCropHeightValid.value = scaRating in 0..100
    }
}
