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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import studio.zebro.stockr.R
import studio.zebro.stockr.uicompose.navigation.RESEARCH_LISTING_SCREEN_NAV_NAME
import studio.zebro.core.theme.GreyColor
import studio.zebro.core.theme.PrimaryMainColor
import studio.zebro.research.ui.research.ResearchViewModel
import kotlin.math.hypot

@Composable
fun SplashScreen(navController: NavController, researchViewModel: ResearchViewModel = viewModel()) {
    researchViewModel.getStockResearch(false)
    researchViewModel.getNifty50IndexData()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spash_anim))
    val progress by animateLottieCompositionAsState(composition)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (progress == 1f) {
            CircularRevealLayout(Modifier.focusModifier(), navController)
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
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
    navController: NavController
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
                    center = Offset(size.width / 2f, size.height / 2f),
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
            radius = value / 2
        }
        // reset the initial value after finishing animation
        animatedRadius.snapTo(0f)
        navController.navigate(RESEARCH_LISTING_SCREEN_NAV_NAME)
    }
}