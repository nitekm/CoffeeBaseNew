package ncodedev.coffeebase.ui.screens.editcoffee

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.Tag
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.model.enums.RoastProfile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

sealed interface EditCoffeeUiState {
    data class Success(val coffee: Coffee) : EditCoffeeUiState
    object Empty : EditCoffeeUiState
    object Error : EditCoffeeUiState
    object Loading : EditCoffeeUiState
}

@HiltViewModel
class EditCoffeeViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) : ViewModel() {

    var editCoffeeUiState: EditCoffeeUiState by mutableStateOf(EditCoffeeUiState.Loading)
        private set

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
    var tags = mutableStateListOf<Tag>()

    fun getCoffeeToEdit(coffeeId: Long) {
        EditCoffeeUiState.Loading
        viewModelScope.launch {
            try {
                val coffee = coffeeRepository.getCoffee(coffeeId)
                coffeeName.value = coffee.name
                roaster.value = coffee.origin ?: ""
                processing.value = coffee.processing ?: ""
                roastProfile.value = coffee.roastProfile?.let { RoastProfile.fromRoastProfileValue(it) } ?: RoastProfile.ROAST_PROFILE
                region.value = coffee.region ?: ""
                continent.value = coffee.continent?.let { Continent.fromContinentValue(it) } ?: Continent.CONTINENT
                farm.value = coffee.farm ?: ""
                cropHeight.value = (coffee.cropHeight ?: 0).toString()
                scaRating.value = (coffee.scaRating ?: 0).toString()
                rating.doubleValue = coffee.rating ?: 0.0
                val coffeeTags = SnapshotStateList<Tag>()
                coffeeTags.addAll(coffee.tags)
                tags = coffeeTags
                EditCoffeeUiState.Empty
            } catch (e: IOException) {
                EditCoffeeUiState.Error
            }
        }
    }

    fun saveCoffee(context: Context, coffeeImage: Bitmap?) {
        if (!validateCoffee()) return
        val coffee = Coffee(
            id = null,
            name = coffeeName.value,
            origin = origin.value.takeIf { it.isNotBlank() },
            roaster = roaster.value.takeIf { it.isNotBlank() },
            processing = processing.value.takeIf { it.isNotBlank() },
            roastProfile = roastProfile.value.roastProfileValue,
            region = region.value.takeIf { it.isNotBlank() },
            continent = continent.value.continentValue,
            farm = farm.value.takeIf { it.isNotBlank() },
            cropHeight = cropHeight.value.toIntOrNull(),
            scaRating = scaRating.value.toIntOrNull(),
            rating = rating.doubleValue.takeIf { it > 0.0 },
            coffeeImageName = null,
            favourite = false,
            tags = tags,
        )
        val coffeeImagePart = coffeeImage?.toMultipartBodyPart(context, "image")

        viewModelScope.launch {
            EditCoffeeUiState.Loading
            try {
                EditCoffeeUiState.Success(coffeeRepository.saveCoffee(coffee, coffeeImagePart))
            } catch (e: IOException) {
                EditCoffeeUiState.Error
            }
        }
    }

    private fun validateCoffee(): Boolean {
        if (coffeeName.value.isBlank()) {
            isNameValid.value = false
        }
        if (cropHeight.value.toIntOrNull() != null && cropHeight.value.toIntOrNull() !in 0..8849) {
            isCropHeightValid.value = false
        }
        if (scaRating.value.toIntOrNull() != null && scaRating.value.toIntOrNull() !in 0..99) {
            isScaRatingValid.value = false
        }
        return isNameValid.value && isScaRatingValid.value && isCropHeightValid.value
    }

    private fun Bitmap.toMultipartBodyPart(context: Context, name: String): MultipartBody.Part {
        val file = File.createTempFile(name, ".jpg", context.cacheDir)
        FileOutputStream(file).use { outputStream ->
            this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(name, file.name, requestBody)
        }
    }
}
