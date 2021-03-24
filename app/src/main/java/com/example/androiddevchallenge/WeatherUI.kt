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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.Utils.Utils
import com.example.androiddevchallenge.components.GetLocAnim
import com.example.androiddevchallenge.components.GetWeatherColor
import com.example.androiddevchallenge.components.GetWeatherIcons
import com.example.androiddevchallenge.components.TodaysWeatherList
import com.example.androiddevchallenge.components.WeeklyWeatherDatesListAdapter
import com.example.androiddevchallenge.components.WeeklyWeatherListAdapter
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
        GetWeatherColor(weather = weatherType),
        Utils().GetAnimSpec(weatherType)
    )

    val imageLoc1 by vm.imageLoc1.observeAsState((-150).dp)

    val imageLoc2 by vm.imageLoc2.observeAsState(width.dp)


    val locAnim1 by GetLocAnim(imageLoc1, 10000)

    val locAnim2 by GetLocAnim(imageLoc2, 12000)

    val locAnim3 by GetLocAnim(imageLoc1, 15000)

    Column(Modifier.background(bgAnimColor).fillMaxHeight()) {

        WeatherNow(weatherList, onWeatherClick, modifier, weeklylistState)

        WeekDates(weatherList, onDateClick, modifier, weeklylistState)

        TodaysWeather(weatherList, onDateClick, modifier)
    }

    floaters(weatherType = weatherType,locAnim1 = locAnim1,locAnim2 = locAnim2,locAnim3 = locAnim3)

    vm.setLoc1(width.dp)

    vm.setLoc2((-150).dp)
}

@Composable
private fun WeatherNow(
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
private fun WeekDates(
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
private fun TodaysWeather(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TodaysWeatherList(
        dates = dates,
        onDateClick = onDateClick,
        modifier = modifier
    )
}

@Composable
private fun floaters(weatherType:String,locAnim1: Dp,locAnim2:Dp, locAnim3:Dp)
{
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var offsetX1 by remember { mutableStateOf(width.toFloat() - 100f) }
    var offsetY1 by remember { mutableStateOf(width.toFloat() - 100f) }

    Image(
        painter = GetWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = weatherType,
        modifier = Modifier.padding(top = 100.dp).size(50.dp).offset(locAnim2)
    )

    Image(
        painter = GetWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = weatherType,
        modifier = Modifier.padding(top = 200.dp).size(50.dp).offset(locAnim1)
    )

    Image(
        painter = GetWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = weatherType,
        modifier = Modifier.padding(top = 300.dp).size(50.dp).offset(locAnim3)
    )

    Image(
        painter = GetWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = weatherType,
        modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }.padding(top = 400.dp).size(50.dp).pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }
    )

    Image(
        painter = GetWeatherIcons(weather = weatherType),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = weatherType,
        modifier = Modifier.offset { IntOffset(offsetX1.roundToInt(), offsetY1.roundToInt()) }.padding(top = 10.dp).size(50.dp).pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                offsetX1 += dragAmount.x
                offsetY1 += dragAmount.y
            }
        }
    )
}
