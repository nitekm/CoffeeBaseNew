package ncodedev.coffeebase.model

data class User(
    val username: String? = "User",
    val email: String,
    val pictureUri: String,
    val token: String
)
