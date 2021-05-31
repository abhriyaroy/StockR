package studio.zebro.recommendation.ui.recommendation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
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
import studio.zebro.recommendation.ui.transition.RecommendationRecyclerItemTransitionViewsModel

@AndroidEntryPoint
class RecommendationFragment : BaseFragment() {

    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var recommendationsAdapter: RecommendationsRecyclerViewAdapter
    var stockRecommendationModel: StockRecommendationModel? = null

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
                    viewsModel: RecommendationRecyclerItemTransitionViewsModel
                ) {
                    val extras = FragmentNavigatorExtras(
                        viewsModel.rootItem to getString(R.string.stock_parent_view_transition_name),
                        viewsModel.titleTextView to getString(R.string.stock_symbol_transition_name),
                        viewsModel.buyAtTextView to getString(R.string.stock_buyat_transition_name),
                        viewsModel.sellAtTextView to getString(R.string.stock_sellat_transition_name),
                        viewsModel.actionTextView to getString(R.string.stock_action_transition_name),
                        binding.appLogoImageView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME
                    )

                    findNavController().navigate(
                        RecommendationFragmentDirections.actionRecommendationFragmentToRecommendationDetailFragment(
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
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
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
                    if(it.data.isNotEmpty()) {
                        stockRecommendationModel = it.data[4]
                        recommendationsAdapter.setItemsList(it.data)
                    } else {

                    }
                    binding.swipeRefresh.isRefreshing = false
                }
                is ResourceState.Loading -> {
                    Log.d(this.javaClass.name, "loading --->")
                    binding.swipeRefresh.isRefreshing = true
                }
                is ResourceState.Error -> {
                    Log.e(this.javaClass.name, "the error is --> ${it.error?.message}")
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun setupSwipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            recommendationViewModel.getStockRecommendations(true)
        }
    }
}