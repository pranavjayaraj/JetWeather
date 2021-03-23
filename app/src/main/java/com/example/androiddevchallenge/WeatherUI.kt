package com.example.androiddevchallenge

import android.content.res.Resources
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.Utils.Utils
import com.example.androiddevchallenge.components.*
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.repository.WeatherRepository
import com.example.androiddevchallenge.viewModel.WeatherViewModel
import kotlinx.coroutines.delay

val width = (Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.scaledDensity).toInt()

var vm = WeatherViewModel()

@Composable
fun WeatherLayout(
    onWeatherClick: (Long) -> Unit, onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val weatherList = remember { WeatherRepository.getWeeklyWeather() }

    val weeklylistState = rememberLazyListState()

    val weatherType by vm.weatherType.observeAsState("Thunder")

    val bgAnimColor by animateColorAsState(
        getWeatherColor(weather = weatherType) ,
        Utils().getAnimSpec(weatherType)
    )

    val imageLoc by vm.imageLoc.observeAsState((-150).dp)

    val imageLoc2 by vm.imageLoc2.observeAsState(width.dp)


    val locAnim by animateDpAsState(targetValue = imageLoc,
    animationSpec = infiniteRepeatable(
        animation = tween(10000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart,
    ))

    val locAnim2 by animateDpAsState(targetValue = imageLoc2,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ))

    val locAnim3 by animateDpAsState(targetValue = imageLoc,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ))

    Column(Modifier.background(bgAnimColor).fillMaxHeight()) {

        Feed(weatherList, onWeatherClick, modifier, weeklylistState)

        Dates(weatherList, onDateClick, modifier, weeklylistState)

        DayWeathers(weatherList, onDateClick, modifier)
    }

    Image(
        painter = getWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier.padding(top = 100.dp).size(50.dp).offset(locAnim2)
    )

    Image(
        painter = getWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier.padding(top = 200.dp).size(50.dp).offset(locAnim)
    )

    Image(
        painter =getWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier.padding(top = 300.dp).size(50.dp).offset(locAnim3)
    )

    vm.imageLoc.postValue(width.dp)

    vm.imageLoc2.postValue((-150).dp)

}

@Composable
private fun Feed(
    weeklyWeather: List<WeeklyWeather>,
    onWeatherClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState
) {
    WeeklyWeatherListAdapter(
        weeklyWeather = weeklyWeather,
        onWeatherClick = onWeatherClick,
        modifier = modifier,
        state = state
    )
}

@Composable
private fun Dates(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState) {
    WeeklyWeatherDatesListAdapter(
        dates = dates,
        onDateClick = onDateClick,
        modifier = modifier,
        weeklyState = state)
}

@Composable
private fun DayWeathers(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier) {
    DayWeatherList(
        dates = dates,
        onDateClick = onDateClick,
        modifier = modifier)
}
