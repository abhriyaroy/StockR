package studio.zebro.stockr.uicompose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 12.sp
    ),
)