package ncodedev.coffeebase.network

import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.Page
import ncodedev.coffeebase.model.PageCoffeeRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CoffeeApiService {

    @POST("coffees/find")
    suspend fun getCoffeesPaged(@Body request: PageCoffeeRequest) : Page<Coffee>
}