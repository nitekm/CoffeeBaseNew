package ncodedev.coffeebase.mocks

import ncodedev.coffeebase.data.repository.IUserRepository
import ncodedev.coffeebase.model.User

class MockUserRepository: IUserRepository {

    override val user: User = User("testUser", "testEmail", "testUri", "testToken")
    override val token: String get() = user.token ?: ""

}