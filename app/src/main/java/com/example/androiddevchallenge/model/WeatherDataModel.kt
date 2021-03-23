/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.model

import androidx.compose.runtime.Immutable
import com.example.androiddevchallenge.R

@Immutable
data class Weather(
    val id: Long,
    val degree: String,
    val wind: String,
    val precipitation: Int,
    val weatherType: String = "",
    val time: String = "",
    val date: String = "",
    val drawable: Int
)

@Immutable
data class WeeklyWeather(val date: String, val dailyWeatherInfo: List<Weather>)

data class WeeklyDates(val date: String)

val weatherInfo = listOf(
    Weather(
        id = 1,
        degree = "15",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Thunder",
        time = "9 am",
        date = "Tue,23 mar",
        drawable = R.drawable.lightning
    ),
    Weather(
        id = 2,
        degree = "19",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Cloudy",
        time = "10 am",
        date = "Tue,23 mar",
        drawable = R.drawable.cloudy
    ),
    Weather(
        id = 3,
        degree = "12",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Storm",
        time = "11 am",
        date = "Tue,23 mar",
        drawable = R.drawable.storm

    ),
    Weather(
        id = 4,
        degree = "11",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Cool",
        time = "6 am",
        date = "Tue,23 mar",
        drawable = R.drawable.cold
    ),
    Weather(
        id = 5,
        degree = "8",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Storm",
        time = "7 am",
        date = "Tue,24 mar",
        drawable = R.drawable.storm
    ),
    Weather(
        id = 6,
        degree = "9",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Cool",
        time = "8 am",
        date = "Tue,24 mar",
        drawable = R.drawable.volcano
    ),
    Weather(
        id = 7,
        degree = "10",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Rain",
        time = "6 am",
        date = "Tue,25 mar",
        drawable = R.drawable.rain
    ),
    Weather(
        id = 8,
        degree = "11",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Eclipse",
        time = "7 am",
        date = "Tue,25 mar",
        drawable = R.drawable.eclipse
    ),
    Weather(
        id = 9,
        degree = "14",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Hurricane",
        time = "8 am",
        date = "Tue,25 mar",
        drawable = R.drawable.hurricane
    ),
    Weather(
        id = 10,
        degree = "30",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Sunny",
        time = "6 am",
        date = "Tue,25 mar",
        drawable = R.drawable.sunrise
    ),
    Weather(
        id = 11,
        degree = "21",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Rainy",
        time = "7 am",
        date = "Tue,25 mar",
        drawable = R.drawable.rainy
    ),
    Weather(
        id = 12,
        degree = "20",
        wind = "2km/h",
        precipitation = 5,
        weatherType = "Hurricane",
        time = "18 am",
        date = "Tue,25 mar",
        drawable = R.drawable.hurricane
    ),
    Weather(
        id = 13,
        degree = "22",
        wind = "4km/h",
        precipitation = 5,
        weatherType = "Hurricane",
        time = "6 am",
        date = "Tue,25 mar",
        drawable = R.drawable.hurricane
    ),
    Weather(
        id = 14,
        degree = "26",
        wind = "3km/h",
        precipitation = 5,
        weatherType = "Rainy",
        time = "7 am",
        date = "Tue,25 mar",
        drawable = R.drawable.rainy
    ),
    Weather(
        id = 15,
        degree = "26",
        wind = "1km/h",
        precipitation = 5,
        weatherType = "Storm",
        time = "8 am",
        date = "Tue,25 mar",
        drawable = R.drawable.storm
    )
)

// faking the weather data as if received from api
val weeklyWeatherInfo = listOf(
    WeeklyWeather("Tue,23 mar", weatherInfo.subList(0, 3)),
    WeeklyWeather("Wed,24 mar", weatherInfo.subList(3, 6)),
    WeeklyWeather("Thur,25 mar", weatherInfo.subList(6, 9)),
    WeeklyWeather("Fri,26 mar", weatherInfo.subList(9, 12)),
    WeeklyWeather("Sat,27 mar", weatherInfo.subList(12, 15))
)
