package ncodedev.coffeebase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ncodedev.coffeebase.data.repository.IUserRepository
import ncodedev.coffeebase.mocks.MockUserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class TestModule {
    @Binds
    abstract fun bindUserRepository(userRepository: MockUserRepository) : IUserRepository
}