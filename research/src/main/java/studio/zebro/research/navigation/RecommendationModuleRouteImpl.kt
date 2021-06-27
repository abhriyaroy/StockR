package studio.zebro.research.navigation

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import studio.zebro.core.navigation.ResearchModuleRoute
import studio.zebro.core.navigation.TransitionNameConstants
import studio.zebro.research.R


class ResearchModuleRouteImpl : ResearchModuleRoute {
    override fun populateView(
        navController: NavController,
        transitionView: ImageView?
    ) {
        navController.navigate(R.id.nav_graph_research,
            null,
            null,
            transitionView?.let {
                FragmentNavigatorExtras(
                    transitionView to TransitionNameConstants.SPLASH_TO_RESEARCH_LOGO_TRANSITION_NAME
                )
            })
    }
}