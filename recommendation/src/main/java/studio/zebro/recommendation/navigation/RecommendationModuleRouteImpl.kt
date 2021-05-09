package studio.zebro.recommendation.navigation

import androidx.navigation.NavController
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.recommendation.R

class RecommendationModuleRouteImpl : RecommendationModuleRoute {
    override fun populateView(navController: NavController) {
        navController.navigate(R.id.nav_graph_recommendation)
    }
}