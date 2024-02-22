package ncodedev.coffeebase.model

data class Page<T>(
    val content: List<T>,
    val isLastPage: Boolean,
    val pageNumber: Int
)
