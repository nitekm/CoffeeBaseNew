package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.PageCoffeeRequest
import ncodedev.coffeebase.network.CoffeeApiService
import javax.inject.Inject

class CoffeeRepository @Inject constructor(
    private val coffeeApiService: CoffeeApiService
) {
    var coffees: List<Coffee> = emptyList()
    suspend fun getCoffeesPaged(request: PageCoffeeRequest) = coffeeApiService.getCoffeesPaged(request)


}