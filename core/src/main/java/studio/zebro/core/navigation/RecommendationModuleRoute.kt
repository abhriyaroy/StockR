package studio.zebro.core.navigation

import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView

interface RecommendationModuleRoute {
    fun populateView(navController: NavController, transitionView: LottieAnimationView? = null)
}