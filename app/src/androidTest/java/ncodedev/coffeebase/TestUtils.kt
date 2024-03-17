package ncodedev.coffeebase

import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.Tag

fun createTestCoffee(
    id: Long = 1L,
    name: String = "test coffee",
    favourite: Boolean = false,
    origin: String = "default origin",
    roaster: String = "default roaster",
    processing: String = "default processing",
    roastProfile: String = "default profile",
    region: String = "default region",
    continent: String = "default continent",
    farm: String = "default farm",
    cropHeight: Int = 2000,
    scaRating: Int = 80,
    rating: Double = 2.0,
    coffeeImageName: String = "test image name",
    tags: List<Tag> = emptyList()
): Coffee {
    return Coffee(
        id,
        name,
        favourite,
        origin,
        roaster,
        processing,
        roastProfile,
        region,
        continent,
        farm,
        cropHeight,
        scaRating,
        rating,
        coffeeImageName,
        tags
    )
}