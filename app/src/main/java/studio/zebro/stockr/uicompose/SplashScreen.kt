package studio.zebro.stockr.uicompose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import studio.zebro.stockr.R
import studio.zebro.stockr.uicompose.theme.GreyColor
import studio.zebro.stockr.uicompose.theme.PrimaryMainColor
import kotlin.math.hypot

@Preview
@Composable
fun SplashScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spash_anim))
    val progress by animateLottieCompositionAsState(composition)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if(progress == 1f){
            CircularRevealLayout(Modifier.focusModifier())
        }
        Box(modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp)){
            LottieAnimation(
                composition,
                progress,
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.research_warning_message),
                textAlign = TextAlign.Center,
                color = GreyColor,
            )
        }
    }
}


@Composable
fun CircularRevealLayout(
    modifier: Modifier = Modifier,
) {
    var radius by remember { mutableStateOf(0f) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .drawBehind {
                drawCircle(
                    color = PrimaryMainColor,
                    radius = radius,
                    center = Offset(size.width/2f, size.height/2f),
                )
            },
        contentAlignment = Alignment.Center
    ) {}
    val animatedRadius = remember { Animatable(0f) }
    val (width, height) = with(LocalConfiguration.current) {
        with(LocalDensity.current) { screenWidthDp.dp.toPx() to screenHeightDp.dp.toPx() }
    }
    val maxRadiusPx = hypot(width, height)
    LaunchedEffect(false) {
        animatedRadius.animateTo(maxRadiusPx, animationSpec = tween()) {
            radius = value/2
        }
        // reset the initial value after finishing animation
        animatedRadius.snapTo(0f)
    }
}