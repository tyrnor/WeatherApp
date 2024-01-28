package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.domain.exceptions.CustomException
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class FetchCurrentWeatherUseCaseTest {

    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase
    private val mockWeatherRepository: WeatherRepository = mock()

    // Expected domain model based on the mock API response
    private val expectedCurrentWeather = CurrentWeatherModel(
        time = "2024-01-25T11:00",
        temperature = 13.4,
        apparentTemperature = 12.0,
        weatherCode = 3
    )


    // Mock API response
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
    fun `fetch current weather use case returns expected data`() = runTest {
        // Arrange
        val latitude = 40.42
        val longitude = -3.6999998

        whenever(mockWeatherRepository.getCurrentWeather(latitude, longitude))
            .thenReturn(mockCurrentWeatherResponse)

        // Act
        val result = fetchCurrentWeatherUseCase(latitude, longitude)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedCurrentWeather, result.getOrNull(), "Use case should transform data into CurrentWeather domain model correctly")
    }

    @Test
    fun `fetch current weather use case handles network error`() = runTest {
        // Arrange
        val latitude = 40.42
        val longitude = -3.6999998
        whenever(mockWeatherRepository.getCurrentWeather(latitude, longitude)).thenThrow(RuntimeException("Network error"))

        // Act
        val result = fetchCurrentWeatherUseCase(latitude, longitude)

        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CustomException)
        assertEquals("An unexpected error occurred", result.exceptionOrNull()?.message)
    }

    // Additional tests for other error scenarios...
}

