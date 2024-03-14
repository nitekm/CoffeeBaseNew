package ncodedev.coffeebase.network

import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.Page
import ncodedev.coffeebase.model.PageCoffeeRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface CoffeeApiService {

    @POST("coffees/find")
    suspend fun getCoffeesPaged(@Body request: PageCoffeeRequest) : Page<Coffee>

    @GET("coffees/search")
    suspend fun searchCoffees(@Query("content") searchBy: String): List<Coffee>

    @POST("coffees")
    @Multipart
    suspend fun saveCoffee(@Part("coffee") coffee: Coffee, @Part image: MultipartBody.Part? = null)
}