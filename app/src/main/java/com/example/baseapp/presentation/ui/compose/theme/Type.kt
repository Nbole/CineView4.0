package com.example.baseapp.presentation.ui.compose

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.baseapp.R

val rubik = FontFamily(
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_medium, FontWeight.Bold)
)

val MTypography = Typography(
    h1 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    h5 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    caption = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)