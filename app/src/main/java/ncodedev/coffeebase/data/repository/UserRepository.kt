package ncodedev.coffeebase.data.repository

import ncodedev.coffeebase.model.User
import javax.inject.Inject
import javax.inject.Singleton


interface IUserRepository {
    val user: User?
    val token: String
}

@Singleton
class UserRepository @Inject constructor() : IUserRepository {
    override val user: User? = null
    override val token: String get() = user?.token ?: ""
}