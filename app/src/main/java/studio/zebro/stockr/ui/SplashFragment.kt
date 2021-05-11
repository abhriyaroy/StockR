package studio.zebro.stockr.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.core.util.addOnAnimationListener
import studio.zebro.core.util.gone
import studio.zebro.core.util.visible
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.recommendation.ui.RecommendationViewModel
import studio.zebro.stockr.databinding.FragmentSplashBinding
import javax.inject.Inject
import kotlin.math.max

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
            addOnAnimationListener(
                onAnimationEnd = { showBackgroundAnimation(showRecommendationsScreenWithDelayAndSharedTransition()) }
            )
        }
    }


    private fun showBackgroundAnimation(onAnimationEnd: () -> Unit) {
        splashFragmentBinding.backgroundView.apply {
            val cx = this.measuredWidth / 2
            val cy = this.measuredHeight / 2
            val finalRadius = max(this.width, this.height) / 2
            ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius.toFloat())
                .let {
                    this.visibility = View.VISIBLE
                    it.start()
                    it.addOnAnimationListener(onAnimationEnd = {
                        onAnimationEnd()
                    })
                }
        }
    }

    private fun showRecommendationsScreenWithDelayAndSharedTransition(): () -> Unit =  {
        splashFragmentBinding.splashLogoImageView.visible()
        splashFragmentBinding.splashLogoLottieView.gone()
        withDelayOnMain(100) {
            recommendationModuleRoute.populateView(
                this@SplashFragment.findNavController(),
                splashFragmentBinding.splashLogoImageView
            )
        }
    }

}