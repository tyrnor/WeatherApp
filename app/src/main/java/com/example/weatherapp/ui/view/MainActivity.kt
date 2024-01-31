package com.example.weatherapp.ui.view

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.example.weatherapp.ui.state.WeatherState
import com.example.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

private const val REQUEST_LOCATION_PERMISSION = 1

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
    }

    @Composable
    fun WeatherApp() {
        val currentWeather by mainViewModel.currentWeatherState.collectAsState()
        val hourlyWeather by mainViewModel.hourlyWeatherState.collectAsState()
        val dailyWeather by mainViewModel.dailyWeatherState.collectAsState()
        val cityName by mainViewModel.cityName.collectAsState()
        val minTemp by mainViewModel.minTemp.collectAsState()
        val maxTemp by mainViewModel.maxTemp.collectAsState()
        val background by mainViewModel.background.collectAsState()
        val isDay by mainViewModel.isDay.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(
                        if (isDay == 1) 0xFF4780d3 else 0xFF000000
                    )
                )
        ) {
            SubcomposeAsyncImage(
                model = background,
                loading = { CircularProgressIndicator() },
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when (currentWeather) {
                    is WeatherState.Loading -> CurrentLoadingView()
                    is WeatherState.Success -> CurrentWeatherView(
                        weather = (currentWeather as WeatherState.Success.CurrentWeatherSuccess).data,
                        cityName = cityName,
                        minTemp = minTemp,
                        maxTemp = maxTemp
                    )

                    is WeatherState.Error -> ErrorView(message = (currentWeather as WeatherState.Error).message)
                }
                when (hourlyWeather) {
                    is WeatherState.Loading -> HourlyLoadingView()
                    is WeatherState.Success -> HourlyWeatherView(
                        weather = (hourlyWeather as WeatherState.Success.HourlyWeatherSuccess).data
                    )

                    is WeatherState.Error -> ErrorView(message = (hourlyWeather as WeatherState.Error).message)
                }
                when (dailyWeather) {
                    is WeatherState.Loading -> DailyLoadingView()
                    is WeatherState.Success -> {
                        DailyWeatherView(
                            weather = (dailyWeather as WeatherState.Success.DailyWeatherSuccess).data
                        )
                    }

                    is WeatherState.Error -> ErrorView(message = (dailyWeather as WeatherState.Error).message)
                }
            }
        }

    }


    @Composable
    fun ErrorView(message: String) {
        Text(text = message)
    }

    private fun requestLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            setContent {
                WeatherApp()
            }
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your location to show the weather data.",
                REQUEST_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            setContent {
                WeatherApp()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            setContent {
                WeatherApp()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}



