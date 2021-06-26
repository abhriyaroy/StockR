package studio.zebro.recommendation.ui.recommendation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
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
import studio.zebro.core.util.CoreUtility.getStockUpOrDownColor
import studio.zebro.core.util.gone
import studio.zebro.core.util.showAnimation
import studio.zebro.core.util.visible
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.R
import studio.zebro.recommendation.databinding.FragmentRecommendationBinding
import studio.zebro.recommendation.domain.model.NiftyIndexesDayModel
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
        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        binding.niftyIndexCardView.showAnimation(R.anim.scale_up)
        recommendationViewModel.getNifty50IndexData()
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
                    handleStockRecommendationSuccessState(it)
                }
                is ResourceState.Loading -> {
                    handleStockRecommendationLoadingState()
                }
                is ResourceState.Error -> {
                    handleStockRecommendationErrorState()
                }
            }
        })

        recommendationViewModel.nifty50IndexData.observe(viewLifecycleOwner, {
            when (it) {
                is ResourceState.Success -> {
                    handleNifty50IndexSuccessState(it)
                }
                is ResourceState.Loading -> {
                    handleNifty50IndexLoadingState()
                }
                is ResourceState.Error -> {
                    refreshNifty50IndexAfterDelay()
                }
            }
        })
    }

    private fun setupSwipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            recommendationViewModel.getStockRecommendations(true)
            recommendationViewModel.getNifty50IndexData()
        }
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }

    private fun handleStockRecommendationSuccessState(resourceState: ResourceState.Success<List<StockRecommendationModel>>) {
        if (resourceState.data.isNotEmpty()) {
            stockRecommendationModel = resourceState.data[4]
            recommendationsAdapter.setItemsList(resourceState.data)
            binding.layoutEmptyRecommendations.rootViewGroup.gone()
        } else {
            binding.layoutEmptyRecommendations.rootViewGroup.visible()
        }
        binding.swipeRefresh.isRefreshing = false
    }

    private fun handleStockRecommendationLoadingState() {
        binding.swipeRefresh.isRefreshing = true
        binding.layoutEmptyRecommendations.rootViewGroup.gone()
    }

    private fun handleStockRecommendationErrorState() {
        binding.swipeRefresh.isRefreshing = false
        if (recommendationsAdapter.getVisibleItemsList().isEmpty()) {
            binding.layoutEmptyRecommendations.rootViewGroup.visible()
        }
    }

    private fun handleNifty50IndexSuccessState(resourceState: ResourceState.Success<NiftyIndexesDayModel>) {
        binding.indexValueTextView.text = resourceState.data.value.toString()
        binding.indexChangeValueTextView.text = resourceState.data.changeValue
        binding.indexChangePercentageTextView.text = resourceState.data.changePercentage
        binding.indexChangeImageView.apply {
            if (resourceState.data.isPositiveChange) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_drop_up
                    )!!.mutate().apply {
                        setTint(ContextCompat.getColor(requireContext(), R.color.positive))
                    })
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_drop_down
                    )!!.mutate().apply {
                        setTint(ContextCompat.getColor(requireContext(), R.color.negative))
                    })
            }
            binding.indexChangeValueTextView.setTextColor(
                getStockUpOrDownColor(
                    requireContext(),
                    resourceState.data.isPositiveChange
                )
            )
            binding.indexChangePercentageTextView.setTextColor(
                getStockUpOrDownColor(
                    requireContext(),
                    resourceState.data.isPositiveChange
                )
            )
        }
        binding.niftyIndexCardProgressView.gone()
        refreshNifty50IndexAfterDelay()
    }

    private fun handleNifty50IndexLoadingState() {
        if (recommendationViewModel.nifty50IndexData.value == null) {
            binding.niftyIndexCardProgressView.visible()
        }
    }

    private fun refreshNifty50IndexAfterDelay() {
        withDelayOnMain(5000) {
            recommendationViewModel.getNifty50IndexData()
        }
    }
}