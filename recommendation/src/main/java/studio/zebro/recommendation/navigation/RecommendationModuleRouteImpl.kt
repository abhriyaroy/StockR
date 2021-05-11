package studio.zebro.recommendation.navigation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.lottie.LottieAnimationView
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.core.navigation.TransitionNameConstants
import studio.zebro.core.util.CoreUtility
import studio.zebro.recommendation.R


class RecommendationModuleRouteImpl : RecommendationModuleRoute {
    override fun populateView(
        navController: NavController,
        transitionView: LottieAnimationView?
    ) {
        navController.navigate(R.id.nav_graph_recommendation,
            null,
            null,
            transitionView?.let {
                FragmentNavigatorExtras(
                    transitionView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME
                )
            })
    }
}