package studio.zebro.recommendation.ui.recommendationdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import studio.zebro.core.BaseFragment
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.recommendation.databinding.FragmentRecommendationDetailBinding

class RecommendationDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentRecommendationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRecommendationDetailBinding.inflate(inflater).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.stockRecommendationModelItem =
                RecommendationDetailFragmentArgs.fromBundle(it).recommendationItem
//            with(RecommendationDetailFragmentArgs.fromBundle(it).transitionModel) {
//                Log.d(this.javaClass.name, "check last frag 2 $rootItemTransitionName")
//                Log.d(this.javaClass.name, "check last frag 2 $titleTextViewTransitionName")
//                Log.d(this.javaClass.name, "check last frag 2 $sellAtTextViewTransitionName")
//                Log.d(this.javaClass.name, "check last frag 2 $buyAtTextViewTransitionName")
//                Log.d(this.javaClass.name, "check last frag 2 $actionTextViewTransitionName")
//                binding.recommendationDetailCardView.transitionName = rootItemTransitionName
//                binding.itemNameTextView.transitionName = titleTextViewTransitionName
//                binding.itemActionTextView.transitionName = actionTextViewTransitionName
//                binding.itemBuyAtTextView.transitionName = buyAtTextViewTransitionName
//                binding.itemSellAtTextView.transitionName = sellAtTextViewTransitionName
//            }
        }
        withDelayOnMain(1000) {
            startPostponedEnterTransition()
        }
        attachClickListeners()
    }

    private fun attachClickListeners() {

    }

}