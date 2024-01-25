package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FetchCurrentWeatherUseCaseTest {

    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase
    private val mockWeatherRepository: WeatherRepository = mock()

    // Inline mock data
    private val mockCurrentWeatherResponse = CurrentWeatherResponse(
        latitude = 40.42,
        longitude = -3.6999998,
        current = CurrentWeather(
            time = "2024-01-25T11:00",
            temperature2m = 13.4,
            apparentTemperature = 12.0,
            weatherCode = 3
        )
    )

    @Before
    fun setUp() {
        fetchCurrentWeatherUseCase = FetchCurrentWeatherUseCase(mockWeatherRepository)
    }

    @Test
    fun `fetch current weather returns data`() = runTest {
        // Arrange
        val latitude = 40.42
        val longitude = -3.6999998

        whenever(mockWeatherRepository.getCurrentWeather(latitude, longitude))
            .thenReturn(mockCurrentWeatherResponse)

        // Act
        val result = fetchCurrentWeatherUseCase(latitude, longitude)

        // Assert
        assertEquals(mockCurrentWeatherResponse, result, "Current weather data should match the mock response")
    }

    @Test
    fun `fetch current weather handles errors`() = runTest {
        // Arrange
        val latitude = 40.42
        val longitude = -3.6999998
        val exception = RuntimeException("An error occurred")

        whenever(mockWeatherRepository.getCurrentWeather(latitude, longitude))
            .thenThrow(exception)

        // Act & Assert
        try {
            fetchCurrentWeatherUseCase(latitude, longitude)
            assert(false) { "Expected an exception to be thrown" }
        } catch (e: Exception) {
            assertEquals(exception, e, "Exception should match the thrown exception")
        }
    }

    // Additional tests can be added here as needed
}

