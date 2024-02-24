package ncodedev.coffeebase.mocks

import ncodedev.coffeebase.data.repository.IUserRepository
import ncodedev.coffeebase.model.User

class MockUserRepository: IUserRepository {

    override var user: User? = User("testUser", "testEmail", "testUri", "testToken")
    override val token: String get() = user?.token ?: ""

}
