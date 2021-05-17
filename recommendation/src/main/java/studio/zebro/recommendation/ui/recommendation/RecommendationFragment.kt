package studio.zebro.recommendation.ui.recommendation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

@AndroidEntryPoint
class RecommendationFragment : BaseFragment(), ItemsAdapter.ItemAdapterListener {

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
//        animateRecommendationCard()
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
                    textView: TextView
                ) {
                    val extras = FragmentNavigatorExtras(
                        textView to "checker_1",
                        binding.appLogoImageView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME
                    )

                    findNavController().navigate(
                        RecommendationFragmentDirections.actionRecommendationFragmentToRecommendationDetailFragment(
                            stockRecommendationModel!!
                        ),
                        extras
                    )


//                    val extras = FragmentNavigatorExtras(
//                        textView to "transss"
//                    )
//                    val navAction = RecommendationFragmentDirections.actionRecommendationFragmentToImageFragment(863244)
//                    findNavController().navigate(navAction, extras)
                }
            })

//        binding.recommendationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recommendationRecyclerView.adapter = ItemsAdapter(this)
//
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

    override fun onItemClicked(viewGroup: ViewGroup, item: Item) {

//        val extras = FragmentNavigatorExtras(
//            viewGroup to "checker_1",
//            binding.appLogoImageView to TransitionNameConstants.SPLASH_TO_RECOMMENDATION_LOGO_TRANSITION_NAME
//        )
//
//        findNavController().navigate(
//            RecommendationFragmentDirections.actionRecommendationFragmentToRecommendationDetailFragment(
//                stockRecommendationModel!!
//            ),
//            extras
//        )


        val extras = FragmentNavigatorExtras(
            viewGroup to "transss"
        )
        val navAction =
            RecommendationFragmentDirections.actionRecommendationFragmentToImageFragment(863244)
        findNavController().navigate(navAction, extras)
    }

    private fun setupObservers() {
        recommendationViewModel.stockRecommendations.observe(viewLifecycleOwner, {
            when (it) {
                is ResourceState.Success -> {
                    Log.d(this.javaClass.name, "the data is --> ${it.data.size}")
                    stockRecommendationModel = it.data[4]
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
//        binding.swipeRefresh
    }
}