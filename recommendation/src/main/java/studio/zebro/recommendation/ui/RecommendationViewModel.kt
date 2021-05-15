package studio.zebro.recommendation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.domain.RecommendationUseCase
import studio.zebro.recommendation.domain.model.StockRecommendationModel

class RecommendationViewModel @ViewModelInject
constructor(private val recommendationUseCase: RecommendationUseCase) : ViewModel() {

    private val _stockRecommendations: MutableLiveData<ResourceState<List<StockRecommendationModel>>> = MutableLiveData()

    val stockRecommendations: LiveData<ResourceState<List<StockRecommendationModel>>> = _stockRecommendations

    fun getStockRecommendations() {
        viewModelScope.launch {
            recommendationUseCase.fetchRecommendations()
                .collect {
                    when(it){
                        is ResourceState.Success -> {

                        }
                        else -> {
                            _stockRecommendations.postValue(it)
                        }
                    }
                }
        }
    }
}