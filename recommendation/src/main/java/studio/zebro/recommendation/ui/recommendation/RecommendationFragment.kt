package studio.zebro.recommendation.ui.recommendation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.core.BaseFragment
import studio.zebro.core.navigation.TransitionNameConstants
import studio.zebro.core.util.showAnimation
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.R
import studio.zebro.recommendation.databinding.FragmentRecommendationBinding
import studio.zebro.recommendation.domain.model.StockRecommendationModel
import studio.zebro.recommendation.ui.recommendation.adapter.RecommendationItemClickListener
import studio.zebro.recommendation.ui.recommendation.adapter.RecommendationsRecyclerViewAdapter
import studio.zebro.recommendation.ui.transition.RecommendationItemToRecommendationDetailTransitionModel

@AndroidEntryPoint
class RecommendationFragment : BaseFragment() {

    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var recommendationsAdapter: RecommendationsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recommendationViewModel =
            ViewModelProvider(requireActivity())[RecommendationViewModel::class.java]
        return FragmentRecommendationBinding.inflate(inflater)
            .let {
                binding = it
                binding.root
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        animateRecommendationCard()
        initRecyclerView()
        setupObservers()
        setupSwipeRefreshListener()
    }

    private fun animateRecommendationCard() {
        binding.recommendationsCardView.showAnimation(R.anim.scale_up)
    }

    private fun initRecyclerView() {
        recommendationsAdapter = RecommendationsRecyclerViewAdapter(requireContext(),
            object : RecommendationItemClickListener {

                override fun onRecommendationItemClick(
                    position: Int,
                    stockRecommendationModel: StockRecommendationModel,
                    transitionModel: RecommendationItemToRecommendationDetailTransitionModel
                ) {

                    val extras = FragmentNavigatorExtras(
                        transitionModel.rootItem!! to "t_1",
                        transitionModel.titleTextView!! to "t_2",
                        transitionModel.sellAtTextView!! to "t_3",
                        transitionModel.buyAtTextView!! to "t_4",
                        transitionModel.actionTextView!! to "t_5",
                        binding.appLogoImageView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME
                    )

                    findNavController().navigate(
                        RecommendationFragmentDirections.actionRecommendationFragmentToRecommendationDetailFragment(
                            transitionModel,
                            stockRecommendationModel
                        ),
                        extras
                    )
                }
            })
        binding.recommendationRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                .apply {
                    setDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    )
                })
            this.adapter = recommendationsAdapter
        }
    }

    private fun setupObservers() {
        recommendationViewModel.stockRecommendations.observe(viewLifecycleOwner, {
            when (it) {
                is ResourceState.Success -> {
                    Log.d(this.javaClass.name, "the data is --> ${it.data.size}")
                    recommendationsAdapter.setItemsList(it.data)
                }
                is ResourceState.Loading -> {
                    Log.d(this.javaClass.name, "loading --->")
                }
                is ResourceState.Error -> {
                    Log.e(this.javaClass.name, "the error is --> ${it.error?.message}")
                }
            }
        })
    }

    private fun setupSwipeRefreshListener() {
        binding.swipeRefresh
    }
}