package com.example.weatherapp.di

import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.source.remote.OpenMeteoService
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenMeteoService(retrofit: Retrofit): OpenMeteoService {
        return retrofit.create(OpenMeteoService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(service: OpenMeteoService): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}
