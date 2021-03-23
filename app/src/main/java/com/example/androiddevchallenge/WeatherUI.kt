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
package com.example.androiddevchallenge

import android.content.res.Resources
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.Utils.Utils
import com.example.androiddevchallenge.components.DayWeatherList
import com.example.androiddevchallenge.components.WeeklyWeatherDatesListAdapter
import com.example.androiddevchallenge.components.WeeklyWeatherListAdapter
import com.example.androiddevchallenge.components.getWeatherColor
import com.example.androiddevchallenge.components.getWeatherIcons
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.repository.WeatherRepository
import com.example.androiddevchallenge.viewModel.WeatherViewModel
import kotlin.math.roundToInt

val width = (Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.scaledDensity).toInt()

var vm = WeatherViewModel()

@Composable
fun WeatherLayout(
    onWeatherClick: (Long) -> Unit,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val weatherList = remember { WeatherRepository.getWeeklyWeather() }

    val weeklylistState = rememberLazyListState()

    val weatherType by vm.weatherType.observeAsState("Thunder")

    val bgAnimColor by animateColorAsState(
        getWeatherColor(weather = weatherType),
        Utils().getAnimSpec(weatherType)
    )

    val imageLoc by vm.imageLoc.observeAsState((-150).dp)

    val imageLoc2 by vm.imageLoc2.observeAsState(width.dp)

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val locAnim by animateDpAsState(
        targetValue = imageLoc,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    val locAnim2 by animateDpAsState(
        targetValue = imageLoc2,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    val locAnim3 by animateDpAsState(
        targetValue = imageLoc,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

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
        painter = getWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier.padding(top = 300.dp).size(50.dp).offset(locAnim3)
    )

    Image(
        painter = getWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }.padding(top = 300.dp).size(50.dp).pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
                if (dragAmount.x> 50) {
                }
            }
        }
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
    state: LazyListState
) {
    WeeklyWeatherDatesListAdapter(
        dates = dates,
        onDateClick = onDateClick,
        modifier = modifier,
        weeklyState = state
    )
}

@Composable
private fun DayWeathers(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    DayWeatherList(
        dates = dates,
        onDateClick = onDateClick,
        modifier = modifier
    )
}
