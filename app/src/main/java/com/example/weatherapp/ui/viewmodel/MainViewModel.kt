package com.example.weatherapp.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.domain.model.HourlyWeatherModel
import com.example.weatherapp.domain.usecase.FetchCurrentWeatherUseCase
import com.example.weatherapp.domain.usecase.FetchDailyWeatherUseCase
import com.example.weatherapp.domain.usecase.FetchHourlyWeatherUseCase
import com.example.weatherapp.ui.state.WeatherState
import com.example.weatherapp.ui.utils.BackgroundSelector
import com.example.weatherapp.ui.utils.Formatters.formatterDay
import com.example.weatherapp.ui.utils.Formatters.formatterTime
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt


@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    private val fetchHourlyWeatherUseCase: FetchHourlyWeatherUseCase,
    private val fetchDailyWeatherUseCase: FetchDailyWeatherUseCase,
) : AndroidViewModel(application) {


    private val _currentWeatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val currentWeatherState: StateFlow<WeatherState> = _currentWeatherState

    private val _hourlyWeatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val hourlyWeatherState: StateFlow<WeatherState> = _hourlyWeatherState

    private val _dailyWeatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val dailyWeatherState: StateFlow<WeatherState> = _dailyWeatherState

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    private val _minTemp = MutableStateFlow(0)
    val minTemp: StateFlow<Int> = _minTemp

    private val _maxTemp = MutableStateFlow(0)
    val maxTemp: StateFlow<Int> = _maxTemp

    private val _background = MutableStateFlow(0)
    val background: StateFlow<Int> = _background

    private val _isDay = MutableStateFlow(1)
    val isDay: StateFlow<Int> = _isDay


    private var cachedCurrentWeather: CurrentWeatherModel? = null
    private var cachedHourlyWeather: HourlyWeatherModel? = null
    private var cachedDailyWeather: DailyWeatherModel? = null


    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private lateinit var currentLocation: Task<Location>
    private lateinit var startHour: String
    private lateinit var endHour: String

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    init {
        viewModelScope.launch {
            requestCurrentLocation()
            getCurrentDateTime()
            getIsDay(latitude, longitude)
            getCurrentWeather()
        }
    }

    private suspend fun getCurrentWeather() {
        currentLocation.addOnSuccessListener { location ->
            location?.let {
                latitude = it.latitude
                longitude = it.longitude
                _cityName.value = getCityName(latitude, longitude)
                viewModelScope.launch {
                    if (cachedCurrentWeather == null) {
                        fetchCurrentWeatherUseCase(latitude, longitude).let { result ->
                            result.onSuccess { weather ->
                                _background.value =
                                    BackgroundSelector.select(weather.weatherCode, weather.isDay)
                                cachedCurrentWeather = weather
                                _currentWeatherState.value =
                                    WeatherState.Success.CurrentWeatherSuccess(weather)
                            }
                            result.onFailure { throwable ->
                                _currentWeatherState.value =
                                    WeatherState.Error(throwable.message ?: "Unknown Error")
                            }
                        }
                    } else {
                        _background.value = BackgroundSelector.select(
                            cachedCurrentWeather!!.weatherCode,
                            cachedCurrentWeather!!.isDay
                        )
                        _currentWeatherState.value =
                            WeatherState.Success.CurrentWeatherSuccess(cachedCurrentWeather!!)
                    }
                    if (cachedHourlyWeather == null) {
                        fetchHourlyWeatherUseCase(
                            latitude,
                            longitude,
                            startHour,
                            endHour
                        ).let { result ->
                            result.onSuccess { weather ->
                                cachedHourlyWeather = weather
                                _hourlyWeatherState.value =
                                    WeatherState.Success.HourlyWeatherSuccess(weather)
                            }
                            result.onFailure { throwable ->
                                _hourlyWeatherState.value =
                                    WeatherState.Error(throwable.message ?: "Unknown Error")
                            }
                        }
                    } else {
                        _hourlyWeatherState.value =
                            WeatherState.Success.HourlyWeatherSuccess(cachedHourlyWeather!!)
                    }
                    if (cachedDailyWeather == null) {
                        fetchDailyWeatherUseCase(latitude, longitude).let { result ->
                            result.onSuccess { weather ->
                                _minTemp.value = weather.minTemperature[0].roundToInt()
                                _maxTemp.value = weather.maxTemperature[0].roundToInt()
                                cachedDailyWeather = weather
                                _dailyWeatherState.value =
                                    WeatherState.Success.DailyWeatherSuccess(weather)
                            }
                            result.onFailure { throwable ->
                                _dailyWeatherState.value =
                                    WeatherState.Error(throwable.message ?: "Unknown Error")
                            }
                        }
                    } else {
                        _dailyWeatherState.value =
                            WeatherState.Success.DailyWeatherSuccess(cachedDailyWeather!!)
                    }

                }
            } ?: kotlin.run {
                _currentWeatherState.value = WeatherState.Error("Location not available")
            }
        }.addOnFailureListener { exception ->
            _currentWeatherState.value = WeatherState.Error(exception.message ?: "Location error")
        }

    }

    private fun requestCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        currentLocation = fusedLocationClient
            .getCurrentLocation(
                CurrentLocationRequest
                    .Builder()
                    .setMaxUpdateAgeMillis(15000)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build(),
                null
            )
    }

    private fun getCurrentDateTime() {
        val currentDay = LocalDateTime.now().format(formatterDay)
        val nextDay = LocalDateTime.now().plusDays(1).format(formatterDay)
        val currentTime = LocalDateTime.now().format(formatterTime)
        startHour = "${currentDay}T${currentTime}:00"
        endHour = "${nextDay}T${currentTime}:00"
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses?.firstOrNull()?.locality ?: "Unknown Location"
    }


    private suspend fun getIsDay(latitude: Double, longitude: Double) {
        fetchCurrentWeatherUseCase(latitude, longitude).onSuccess {
            _isDay.value = it.isDay
        }
    }
}

