package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.PageCoffeeRequest
import ncodedev.coffeebase.network.CoffeeApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class CoffeeRepository @Inject constructor(
    private val coffeeApiService: CoffeeApiService
) {
    suspend fun getCoffeesPaged(request: PageCoffeeRequest) = coffeeApiService.getCoffeesPaged(request)

    suspend fun searchCoffees(searchBy: String) = coffeeApiService.searchCoffees(searchBy)

    suspend fun saveCoffee(coffee: Coffee, image: MultipartBody.Part?)
        = coffeeApiService.saveCoffee(coffee = coffee, image = image)
}