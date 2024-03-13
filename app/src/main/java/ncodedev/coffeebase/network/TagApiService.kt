package ncodedev.coffeebase.network

import ncodedev.coffeebase.model.Tag
import retrofit2.http.GET

interface TagApiService {

    @GET("/tags")
    suspend fun getTags(): List<Tag>

}