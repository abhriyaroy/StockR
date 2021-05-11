package studio.zebro.stockr.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.core.navigation.TransitionNameConstants
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.recommendation.ui.RecommendationViewModel
import studio.zebro.stockr.R
import studio.zebro.stockr.databinding.FragmentSplashBinding
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    internal lateinit var recommendationModuleRoute: RecommendationModuleRoute
    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var splashFragmentBinding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSplashBinding.inflate(inflater)
            .let {
                splashFragmentBinding = it
                it.root
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommendationViewModel =
            ViewModelProvider(requireActivity())[RecommendationViewModel::class.java]
        recommendationViewModel.getStockRecommendations()
        attachSplashAnimationCompleteListener()
    }

    private fun attachSplashAnimationCompleteListener() {
        splashFragmentBinding.splashLogoLottieView.apply {
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    withDelayOnMain(500) {

                        val extras = FragmentNavigatorExtras(splashFragmentBinding.splashLogoLottieView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME)
                        findNavController().navigate(
                            R.id.nav_graph_recommendation,
                            null,
                            null,
                            extras
                        )

//                        recommendationModuleRoute.populateView(
//                            this@SplashFragment.findNavController(),
//                            splashFragmentBinding.splashLogoLottieView
//                        )
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}

            })
        }
    }

}