package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    var user: User? = null
    val token: String get() = user?.token ?: ""
}