package ncodedev.coffeebase.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ncodedev.coffeebase.network.CoffeeApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModulle {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build())
        .build()

    @Singleton
    @Provides
    fun provideCoffeeApiService(retrofit: Retrofit) = retrofit.create(CoffeeApiService::class.java)
}