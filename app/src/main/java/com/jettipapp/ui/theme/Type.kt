package com.jettipapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jettipapp.R

// Set of Material typography styles to start with

val fonts = FontFamily(
    Font(R.font.lato_black, weight = FontWeight.Black ),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_thin, weight = FontWeight.Thin)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h4 = TextStyle(fontFamily = fonts,
    fontWeight = FontWeight.Black,
    fontSize = 34.sp,
    letterSpacing = 0.25.sp)


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)