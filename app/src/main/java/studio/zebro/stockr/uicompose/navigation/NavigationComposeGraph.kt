package studio.zebro.stockr.uicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import studio.zebro.research.uicompose.ResearchListingScreen
import studio.zebro.stockr.uicompose.SplashScreen

@Composable
fun StockRNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN_NAV_NAME
    ) {
        composable(SPLASH_SCREEN_NAV_NAME) {
            SplashScreen(navController = navController)
        }
        composable(RESEARCH_LISTING_SCREEN_NAV_NAME) {
            ResearchListingScreen(navController = navController)
        }
    }
}

const val SPLASH_SCREEN_NAV_NAME = "splash_screen"
const val RESEARCH_LISTING_SCREEN_NAV_NAME = "research_listing_screen"