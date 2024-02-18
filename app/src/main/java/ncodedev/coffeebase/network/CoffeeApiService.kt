package ncodedev.coffeebase.network

import ncodedev.coffeebase.model.Coffee
import retrofit2.http.GET

interface CoffeeApiService {

    @GET("coffees/find")
    suspend fun getCoffeesPaged() : List<Coffee>
}