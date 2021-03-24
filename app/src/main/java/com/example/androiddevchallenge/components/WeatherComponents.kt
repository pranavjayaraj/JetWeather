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
package com.example.androiddevchallenge.components

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.Utils.Utils
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.model.weatherInfo
import com.example.androiddevchallenge.vm
import com.example.androiddevchallenge.width
import kotlinx.coroutines.launch

var state = false
@Composable
fun WeeklyWeatherListAdapter(
    weeklyWeather: List<WeeklyWeather>,
    onWeatherClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState
) {
    vm.getWeatherData()

    vm.setImageAnim()

    val animSpec by vm.imageAnimDp.observeAsState(0.dp)

    val weather by vm.weatherData.observeAsState()

    val imageAnim by animateDpAsState(
        targetValue = animSpec,
        TweenSpec(500)
    )

    Column(Modifier.padding(top = 30.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(R.drawable.location),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.size(30.dp).offset(20.dp)
            )

            Text(text = "Bangalore", modifier = modifier.padding(start = 30.dp).semantics { heading() }, color = Color.Black)
        }
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            state = state,
        ) {
            if (weather != null) {
                items(weather!!) { it ->
                    weatherItem(it, onWeatherClick, anim = imageAnim)
                }
            }
        }
    }
}

@Composable
fun weatherItem(
    weather: WeeklyWeather,
    onWeatherClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    anim: Dp
) {
    Column(
        modifier = modifier
            .clickable(
                enabled = false,
                onClick = { onWeatherClick(weather.dailyWeatherInfo[0].id) }
            )
            .width(width.dp)
            .padding(top = 30.dp)
            .semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(weather.dailyWeatherInfo[0].drawable),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = weather.dailyWeatherInfo[0].weatherType,
            modifier = Modifier.size(anim)
        )
        Text(
            text = weather.dailyWeatherInfo[0].weatherType,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 20.dp),
            color = Color.White,
            fontSize = 20.sp
        )
        Row() {
            Text(
                text = weather.dailyWeatherInfo[0].degree,
                style = MaterialTheme.typography.h1,
                color = Color.Black,
            )
            Image(
                painter = painterResource(R.drawable.dot),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "Degree",
                modifier = Modifier.padding(top = 10.dp).size(10.dp)
            )
        }
        Row() {
            Image(
                painter = painterResource(R.drawable.wind),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "Wind",
                modifier = Modifier.padding(top = 8.dp).size(20.dp)
            )
            Text(
                text = weather.dailyWeatherInfo[0].wind,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(end = 20.dp, start = 5.dp),
                color = Color.Black
            )
            Image(
                painter = painterResource(R.drawable.drop),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "Precipitation",
                modifier = Modifier.padding(top = 8.dp).size(20.dp)
            )
            Text(
                text = weather.dailyWeatherInfo[0].precipitation.toString(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(end = 20.dp, start = 1.dp),
                color = Color.Black
            )
        }
    }
    vm.restImageAnim()
}

@Composable
fun WeeklyWeatherDatesListAdapter(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    weeklyState: LazyListState
) {
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = modifier.padding(top = 50.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(dates) { it ->
            dateItem(
                date = it.date,
                onDateClick = {

                    coroutineScope.launch {
                        // Animate scroll to the first item

                        weeklyState.animateScrollBy(
                            Utils().getPixelsFromIndex(dates.indexOf(it)),
                            animationSpec = TweenSpec(1000)
                        )
                    }

                    vm.getdailyWeatherData(it.date)

                    vm.getWeatherData()

                    vm.setWeatherType(it.dailyWeatherInfo[0].weatherType)
                }
            )
        }
    }
}

@Composable
fun dateItem(
    date: String,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = date,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(20.dp).clickable(onClick = onDateClick), color = Color.White,
        fontSize = 15.sp
    )
}

@Composable
fun TodaysWeatherList(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val date by vm.dailyWeatherData.observeAsState()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(date ?: weatherInfo) { it ->
            TodaysWeatherItem(weather = it, onDateClick = {})
        }
    }
}

@Composable
fun TodaysWeatherItem(
    weather: Weather,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.height(200.dp).width(150.dp)
            .padding(15.dp)
            .background(colorResource(id = R.color.opaque), shape = RoundedCornerShape(20.dp)).semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weather.time,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(20.dp), color = Color.DarkGray,
            fontSize = 16.sp
        )
        Image(
            painter = getWeatherIcons(weather = weather.weatherType),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = weather.weatherType,
            modifier = Modifier.size(25.dp)
        )
        Row() {
            Text(
                text = weather.degree,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 20.dp), color = Color.DarkGray
            )
            Image(
                painter = painterResource(R.drawable.dot),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.padding(top = 20.dp).size(5.dp)
            )
        }
    }
}

@Composable
fun getWeatherIcons(weather: String): Painter {
    return when (weather) {
        "Thunder" -> painterResource(id = R.drawable.thunderstorm_white)
        "Cool" -> painterResource(id = R.drawable.snowflake_white)
        "Rain" -> painterResource(id = R.drawable.rain_white)
        "Storm" -> painterResource(id = R.drawable.rain_white)
        "Sunny" -> painterResource(id = R.drawable.sunny_white)
        else -> painterResource(id = R.drawable.thunderstorm_white)
    }
}

@Composable
fun getWeatherColor(weather: String): Color {
    return when (weather) {
        "Thunder" -> colorResource(id = R.color.cloudy)
        "Cool" -> colorResource(id = R.color.cool)
        "Rain" -> colorResource(id = R.color.rainy)
        "Sunny" -> colorResource(id = R.color.sunny)
        "Hurricane" -> colorResource(id = R.color.hurricane)
        else -> colorResource(id = R.color.cloudy)
    }
}
