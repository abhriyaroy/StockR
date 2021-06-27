package studio.zebro.core.navigation

import android.widget.ImageView
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView

interface ResearchModuleRoute {
    fun populateView(navController: NavController, transitionView: ImageView? = null)
}