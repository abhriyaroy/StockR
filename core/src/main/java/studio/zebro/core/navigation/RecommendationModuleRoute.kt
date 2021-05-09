package studio.zebro.core.navigation

import androidx.navigation.NavController

interface RecommendationModuleRoute {
    fun populateView(navController: NavController)
}