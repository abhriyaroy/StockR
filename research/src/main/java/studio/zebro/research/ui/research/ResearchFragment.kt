package studio.zebro.research.ui.research

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
import studio.zebro.research.R
import studio.zebro.research.databinding.FragmentResearchBinding
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.domain.model.StockResearchModel
import studio.zebro.research.ui.research.adapter.ResearchItemClickListener
import studio.zebro.research.ui.research.adapter.ResearchRecyclerViewAdapter
import studio.zebro.research.ui.transition.ResearchRecyclerItemTransitionViewsModel

@AndroidEntryPoint
class ResearchFragment : BaseFragment() {

    private lateinit var researchViewModel: ResearchViewModel
    private lateinit var binding: FragmentResearchBinding
    private lateinit var researchsAdapter: ResearchRecyclerViewAdapter
    var stockResearchModel: StockResearchModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        researchViewModel =
            ViewModelProvider(requireActivity())[ResearchViewModel::class.java]
        return FragmentResearchBinding.inflate(inflater)
            .let {
                binding = it
                binding.root
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        animateResearchCard()
        initRecyclerView()
        setupObservers()
        setupSwipeRefreshListener()
        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        binding.niftyIndexCardView.showAnimation(R.anim.scale_up)
        researchViewModel.getNifty50IndexData()
    }

    private fun animateResearchCard() {
        binding.researchsCardView.showAnimation(R.anim.scale_up)
    }

    private fun initRecyclerView() {
        researchsAdapter = ResearchRecyclerViewAdapter(requireContext(),
            object : ResearchItemClickListener {

                override fun onResearchItemClick(
                    position: Int,
                    stockResearchModel: StockResearchModel,
                    viewsModel: ResearchRecyclerItemTransitionViewsModel
                ) {
                    val extras = FragmentNavigatorExtras(
                        viewsModel.rootItem to getString(R.string.stock_parent_view_transition_name),
                        viewsModel.titleTextView to getString(R.string.stock_symbol_transition_name),
                        viewsModel.buyAtTextView to getString(R.string.stock_buyat_transition_name),
                        viewsModel.sellAtTextView to getString(R.string.stock_sellat_transition_name),
                        viewsModel.actionTextView to getString(R.string.stock_action_transition_name),
                        binding.appLogoImageView to TransitionNameConstants.SPLASH_TO_RESEARCH_LOGO_TRANSITION_NAME
                    )

                    findNavController().navigate(
                        ResearchFragmentDirections.actionResearchFragmentToResearchDetailFragment(
                            stockResearchModel
                        ),
                        extras
                    )


                }
            })

        binding.researchRecyclerView.apply {
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
            this.adapter = researchsAdapter
        }
    }

    private fun setupObservers() {
        researchViewModel.stockResearchs.observe(viewLifecycleOwner, {
            when (it) {
                is ResourceState.Success -> {
                    handleStockResearchSuccessState(it)
                }
                is ResourceState.Loading -> {
                    handleStockResearchLoadingState()
                }
                is ResourceState.Error -> {
                    handleStockResearchErrorState()
                }
            }
        })

        researchViewModel.nifty50IndexData.observe(viewLifecycleOwner, {
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
            researchViewModel.getStockResearchs(true)
            researchViewModel.getNifty50IndexData()
        }
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }

    private fun handleStockResearchSuccessState(resourceState: ResourceState.Success<List<StockResearchModel>>) {
        if (resourceState.data.isNotEmpty()) {
            stockResearchModel = resourceState.data[4]
            researchsAdapter.setItemsList(resourceState.data)
            binding.layoutEmptyResearchs.rootViewGroup.gone()
        } else {
            binding.layoutEmptyResearchs.rootViewGroup.visible()
        }
        binding.swipeRefresh.isRefreshing = false
    }

    private fun handleStockResearchLoadingState() {
        binding.swipeRefresh.isRefreshing = true
        binding.layoutEmptyResearchs.rootViewGroup.gone()
    }

    private fun handleStockResearchErrorState() {
        binding.swipeRefresh.isRefreshing = false
        if (researchsAdapter.getVisibleItemsList().isEmpty()) {
            binding.layoutEmptyResearchs.rootViewGroup.visible()
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
        if (researchViewModel.nifty50IndexData.value == null) {
            binding.niftyIndexCardProgressView.visible()
        }
    }

    private fun refreshNifty50IndexAfterDelay() {
        withDelayOnMain(5000) {
            researchViewModel.getNifty50IndexData()
        }
    }
}