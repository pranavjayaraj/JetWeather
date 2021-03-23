package com.example.androiddevchallenge.components

import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.WeeklyWeather
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.Utils.Utils
import com.example.androiddevchallenge.model.Weather
import com.example.androiddevchallenge.model.weatherInfo
import com.example.androiddevchallenge.model.weeklyWeatherInfo
import com.example.androiddevchallenge.viewModel.WeatherViewModel
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

            Text(text = "Bangalore", modifier = modifier.padding(start = 30.dp), color = Color.Black)

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
                    vm.restImageAnim()
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
                onClick = { onWeatherClick(weather.dailyWeatherInfo[0].id) })
            .width(width.dp)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(weather.dailyWeatherInfo[0].drawable),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.size(anim)
        )
        Text(
            text = weather.dailyWeatherInfo[0].weatherType,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 20.dp),
            color = Color.White,
            fontSize = 20.sp
        )
        Text(
            text = weather.dailyWeatherInfo[0].degree,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Black,
        )
        Row() {
            Image(
                painter = painterResource(R.drawable.wind),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.padding(top = 8.dp).size(20.dp)
            )
            Text(
                text = weather.dailyWeatherInfo[0].wind,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(end = 20.dp,start = 5.dp),
                color = Color.Black
            )
            Image(
                painter = painterResource(R.drawable.drop),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.padding(top = 8.dp).size(20.dp)
            )
            Text(
                text = weather.dailyWeatherInfo[0].precipitation.toString(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(end = 20.dp,start = 1.dp),
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
            dateItem(date = it.date, onDateClick = {

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

                vm.setAnim()

            })
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
fun DayWeatherList(
    dates: List<WeeklyWeather>,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    vm.setAnim()

    val date by vm.dailyWeatherData.observeAsState()

    val animSpec by vm.animDp.observeAsState(0.dp)

    val anim by animateDpAsState(
        targetValue = animSpec,
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
            items(date?: weatherInfo) { it ->
                dayWeatherItem(weather = it, onDateClick = {}, anim = anim)
                vm.resetAnim()
        }
    }
}

@Composable
fun dayWeatherItem(
    weather: Weather,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier,
    anim: Dp
) {
    Column(
        modifier = Modifier.height(200.dp).width(150.dp)
            .offset(y = anim)
            .padding(15.dp)
            .background(colorResource(id = R.color.opaque), shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
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
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = weather.degree,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(20.dp), color = Color.DarkGray
        )
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

