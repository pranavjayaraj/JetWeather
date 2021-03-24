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
package com.example.androiddevchallenge.viewModel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.model.weeklyWeatherInfo

class WeatherViewModel {

    val weatherData = MutableLiveData<List<WeeklyWeather>>()

    val dailyWeatherData = MutableLiveData<List<Weather>>()

    val imageAnimDp = MutableLiveData<Dp>()

    val weatherType = MutableLiveData<String>()

    val imageLoc1 = MutableLiveData<Dp>()

    val imageLoc2 = MutableLiveData<Dp>()

    fun getWeatherData() {
        weatherData.postValue(weeklyWeatherInfo)
    }

    fun getdailyWeatherData(date: String) {
        dailyWeatherData.postValue(weeklyWeatherInfo.firstOrNull { it.date == date }?.dailyWeatherInfo)
    }

    fun setImageAnim() {
        imageAnimDp.postValue(0.dp)
    }

    fun restImageAnim() {
        imageAnimDp.postValue(200.dp)
    }

    fun setWeatherType(weather: String) {
        weatherType.postValue(weather)
    }

    fun setLoc1(dp:Dp)
    {
        imageLoc1.postValue(dp)
    }

    fun setLoc2(dp:Dp)
    {
        imageLoc2.postValue(dp)
    }

}
