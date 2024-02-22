package ncodedev.coffeebase.model

data class PageCoffeeRequest(
    val pageSize: Int = 20,
    val pageNumber: Int = 0,
    val sortProperty: String = "id",
    val sortDirection: String = "ASC",
    val filters: Map<String, Set<String>> = emptyMap()
)
