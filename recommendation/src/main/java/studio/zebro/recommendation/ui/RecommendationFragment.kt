package studio.zebro.recommendation.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.core.util.showAnimation
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.R
import studio.zebro.recommendation.databinding.FragmentRecommendationBinding
import studio.zebro.recommendation.ui.adapter.RecommendationsRecyclerViewAdapter

@AndroidEntryPoint
class RecommendationFragment : Fragment() {

    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var recommendationsAdapter: RecommendationsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

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
        animateRecommendationCard()
        initRecyclerView()
        setupObservers()
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
    }

    private fun animateRecommendationCard() {
        binding.recommendationsCardView.showAnimation(R.anim.scale_up)
    }

    private fun initRecyclerView() {
        recommendationsAdapter = RecommendationsRecyclerViewAdapter(requireContext())
        binding.recommendationRecyclerView.apply {
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
}