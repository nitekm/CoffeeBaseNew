package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.network.CoffeeApiService
import javax.inject.Inject

class CoffeeRepository @Inject constructor(
    private val coffeeApiService: CoffeeApiService
) {
    suspend fun getCoffeesPaged() = coffeeApiService.getCoffeesPaged()
}