package studio.zebro.recommendation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.databinding.RecommendationFragmentBinding

@AndroidEntryPoint
class RecommendationFragment : Fragment() {

    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var binding: RecommendationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recommendationViewModel =
            ViewModelProvider(requireActivity())[RecommendationViewModel::class.java]
        return RecommendationFragmentBinding.inflate(inflater)
            .let {
                binding = it
                binding.root
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        recommendationViewModel.getStockRecommendations()
    }

    private fun setupObservers(){
        recommendationViewModel.stockRecommendations.observe(viewLifecycleOwner, {
            when(it){
                is ResourceState.Success -> {
                    Log.d(this.javaClass.name, "the data is --> ${it.data.size}")
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