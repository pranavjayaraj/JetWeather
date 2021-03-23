package com.example.androiddevchallenge.viewModel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.model.weeklyWeatherInfo

class WeatherViewModel {

    val weatherData = MutableLiveData<List<WeeklyWeather>>()

    val dailyWeatherData = MutableLiveData<List<Weather>>()

    val animDp = MutableLiveData<Dp>()

    val imageAnimDp = MutableLiveData<Dp>()

    val weatherType = MutableLiveData<String>()

    val imageLoc = MutableLiveData<Dp>()

    val imageLoc2 = MutableLiveData<Dp>()

    fun getWeatherData()
    {
        weatherData.postValue(weeklyWeatherInfo)
    }

    fun getdailyWeatherData(date:String)
    {
        dailyWeatherData.postValue(weeklyWeatherInfo.firstOrNull{it.date == date}?.dailyWeatherInfo)
    }

    fun setAnim()
    {
        animDp.postValue(250.dp)
    }

    fun resetAnim()
    {
        animDp.postValue(0.dp)
    }

    fun setImageAnim()
    {
        imageAnimDp.postValue(0.dp)
    }

    fun restImageAnim()
    {
        imageAnimDp.postValue(200.dp)
    }

    fun setWeatherType(weather:String)
    {
        weatherType.postValue(weather)
    }

}