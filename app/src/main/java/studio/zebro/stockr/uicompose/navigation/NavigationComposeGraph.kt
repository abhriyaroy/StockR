package studio.zebro.stockr.uicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import studio.zebro.stockr.uicompose.SplashScreen

@Composable
fun StockRNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
    }
}