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
package com.example.androiddevchallenge.Utils

import android.content.res.Resources
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.currentIndex

class Utils {

    fun GetPixelsFromIndex(index: Int): Float {
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

    fun GetAnimSpec(spec: String): AnimationSpec<Color> {

        return when (spec) {
            "Thunder" -> spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )

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

            "Sunny" ->
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
