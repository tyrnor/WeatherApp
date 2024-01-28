package com.example.weatherapp.ui.view

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.weatherapp.domain.model.CurrentWeatherModel
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
    fun WeatherApp(isPermissionGranted: Boolean) {
        val currentWeather by mainViewModel.currentWeatherState.collectAsState()
        val cityName by mainViewModel.cityName.collectAsState()
        val canFetchData by remember { mutableStateOf(isPermissionGranted) }

        LaunchedEffect(key1 = Unit) {
            if (canFetchData) {
                mainViewModel.getCurrentWeather()
            }
        }

        when (currentWeather) {
            is WeatherState.Loading -> LoadingView()
            is WeatherState.Success -> CurrentWeatherView(
                weather = (currentWeather as WeatherState.Success.CurrentWeatherSuccess).data,
                cityName = cityName
            )

            is WeatherState.Error -> ErrorView(message = (currentWeather as WeatherState.Error).message)
        }

    }

    @Composable
    fun ErrorView(message: String) {
        Text(text = message)
    }

    @Composable
    fun CurrentWeatherView(weather: CurrentWeatherModel, cityName: String) {
        Column {
            Text(text = cityName)
            Text(text = weather.temperature.toString())
        }
    }

    @Composable
    fun LoadingView() {
        Text(text = "Loading")
    }

    private fun requestLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            setContent { WeatherApp(true) }
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your location to show the weather data.",
                REQUEST_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            setContent { WeatherApp(false) }
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
            setContent { WeatherApp(true) }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}



