package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FetchCurrentWeatherUseCaseTest {

    private lateinit var fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase
    private val mockWeatherRepository: WeatherRepository = mock()

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

    // Expected domain model
    private val expectedCurrentWeather = CurrentWeatherModel(
        time = "2024-01-25T11:00",
        temperature = 13.4,
        apparentTemperature = 12.0,
        weatherCode = 3
    )

    @Before
    fun setUp() {
        fetchCurrentWeatherUseCase = FetchCurrentWeatherUseCase(mockWeatherRepository)
    }

    @Test
    fun `fetch current weather use case returns expected data`() = runBlockingTest {
        // Arrange
        val latitude = 40.42
        val longitude = -3.6999998

        whenever(mockWeatherRepository.getCurrentWeather(latitude, longitude))
            .thenReturn(mockCurrentWeatherResponse)

        // Act
        val result = fetchCurrentWeatherUseCase(latitude, longitude)

        // Assert
        assertEquals(expectedCurrentWeather, result, "Use case should transform data into CurrentWeather domain model correctly")
    }

    // Additional tests as needed...
}

