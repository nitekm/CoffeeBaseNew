package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.network.TagApiService
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val tagApiService: TagApiService
) {
    suspend fun getTags() = tagApiService.getTags()
}