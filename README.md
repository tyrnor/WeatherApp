# Android Weather App
## Overview
WeatherApp is a modern Android application designed to provide real-time weather information. 
Utilizing the latest Android development technologies and Jetpack Compose for UI, this app offers an elegant and intuitive user experience. 
It is primarily educational, aimed at showcasing best practices in Android development, including MVVM and Clean Architecture.

![App Screenshot](images/overview.png) ![App Screenshot](images/overview2.png)


## Architecture
WeatherApp adheres to the Model-View-ViewModel (MVVM) architecture, cleanly separating the UI (View) from the data (Model) and business logic (ViewModel). 
It also follows the Clean Architecture principles, dividing the codebase into three distinct layers: Data, Domain, and UI, for enhanced maintainability and scalability.

## Features

### Weather Information

- Current Weather: Displays the current weather details, including temperature, conditions, and location-based data.
- Hourly Forecast: Offers an hourly weather forecast, allowing users to plan their day with precision.
- Daily Forecast: Shows a 7-day weather outlook with key information such as high/low temperatures and general conditions

### User Experience

- Dynamic Background: The background image dynamically changes based on the current weather conditions, enhancing the user's visual experience.
- Location-Based Results: Automatically fetches and displays weather data based on the user's current location.

### Libraries Used
- Jetpack Compose: For building modern, declarative UIs.
- Hilt (Dagger): For dependency injection, ensuring a modular and testable codebase.
- Kotlin Coroutines: For managing asynchronous tasks and handling background processing.
- Retrofit: To handle network requests to the weather API.
- ViewModel: To manage UI-related data in a lifecycle-conscious way.
- Material Design: Implements Material Design guidelines for a polished and cohesive look and feel.

## Possible Enhancements
- Weather Alerts: Integration of severe weather alerts and notifications.
- Customizable Locations: Ability to save and view weather for multiple locations.
- Settings: User preferences for units (Celsius/Fahrenheit), themes, etc.
- Weather Maps: Interactive maps showing weather patterns and radar data.

## License

MIT
