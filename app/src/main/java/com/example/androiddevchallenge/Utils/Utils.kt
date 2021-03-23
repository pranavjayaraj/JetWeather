package com.example.androiddevchallenge.Utils

import android.content.res.Resources
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.currentIndex

class Utils {


    fun getPixelsFromIndex(index: Int): Float {
        var returnVal = 0f
        if (currentIndex < index) {
            returnVal =
                Resources.getSystem().displayMetrics.widthPixels.toFloat() * (index - currentIndex)
        } else if (currentIndex > index) {
            returnVal =
                -(Resources.getSystem().displayMetrics.widthPixels.toFloat() * (currentIndex - index))
        }
        currentIndex = index
        return returnVal
    }

    fun getAnimSpec(spec: String): AnimationSpec<Color> {

        return when (spec) {
            "Thunder" -> spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow,)

            "Cool" -> {
                tween(
                    durationMillis = 2000,
                    delayMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            }
            "Storm" -> {
                infiniteRepeatable(
                    animation = tween(1500, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            }

            "Sunny"->
            {
                tween(
                    durationMillis = 2000,
                    delayMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            }

            else -> {
                tween(
                    durationMillis = 2000,
                    delayMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            }
        }

    }

}