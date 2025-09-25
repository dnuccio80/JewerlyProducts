package com.example.jewerlyproducts.ui.generalfuns

import androidx.compose.ui.graphics.Color
import com.example.jewerlyproducts.ui.theme.LightBrownCard
import com.example.jewerlyproducts.ui.theme.MainColor
import com.example.jewerlyproducts.ui.theme.SecondLightCard

fun getRandomColor(): Color {
    val colorList = listOf(
        MainColor, LightBrownCard, SecondLightCard
    )
    val randomIndex = (0..2).random()
    return colorList[randomIndex]

}