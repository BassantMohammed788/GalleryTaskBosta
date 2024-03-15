package com.example.gallerytaskboosta.di

import com.example.gallerytaskboosta.network.GalleryApi
import com.example.gallerytaskboosta.repository.Repository
import com.example.gallerytaskboosta.repository.RepositoryImpl
import com.example.gallerytaskboosta.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): GalleryApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit
            .Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GalleryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: GalleryApi):Repository{
        return RepositoryImpl(api)
    }
}