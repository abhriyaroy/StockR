package studio.zebro.stockr.uicompose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val ColorPalette = darkColors(
    primary = PrimaryMainColor,
    primaryVariant = PrimaryDarkColor,
    secondary = PrimaryLightColor
)

@Composable
fun StockRTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}