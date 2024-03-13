package ncodedev.coffeebase.model

data class Coffee(
    val id: Long,
    val name: String,
    val favourite: Boolean,
    val origin: String?,
    val roaster: String?,
    val processing: String?,
    val roastProfile: String?,
    val region: String?,
    val continent: String?,
    val farm: String?,
    val cropHeight: Int?,
    val scaRating: Int?,
    val rating: Double?,
    val coffeeImageName: String,
    val tags: List<Tag>
)
