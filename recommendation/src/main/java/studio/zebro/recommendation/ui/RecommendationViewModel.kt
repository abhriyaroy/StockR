package studio.zebro.recommendation.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import studio.zebro.datasource.local.CustomError
import studio.zebro.datasource.model.ErrorModel
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.domain.RecommendationUseCase
import studio.zebro.recommendation.domain.model.StockRecommendationModel

class RecommendationViewModel @ViewModelInject
constructor(private val recommendationUseCase: RecommendationUseCase) : ViewModel() {

    private val _stockRecommendations: MutableLiveData<ResourceState<List<StockRecommendationModel>>> =
        MutableLiveData()

    val stockRecommendations: LiveData<ResourceState<List<StockRecommendationModel>>> =
        _stockRecommendations

    fun getStockRecommendations(isForceRefresh : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _stockRecommendations.postValue(ResourceState.loading())
            recommendationUseCase.fetchRecommendations(isForceRefresh)
                .catch { error ->
                    if (error is CustomError) {
                        _stockRecommendations.postValue(ResourceState.error(error.errorModel))
                    } else {
                        _stockRecommendations.postValue(ResourceState.error(ErrorModel(message = error.message)))
                    }
                }
                .collect {
                    _stockRecommendations.postValue(ResourceState.success(it))
                }
        }
    }

    fun getStockHistoricalData(stockRecommendationModel: StockRecommendationModel) {
        viewModelScope.launch(Dispatchers.IO) {
            recommendationUseCase.fetchHistoricData(stockRecommendationModel.shortName)
                .collect {
                    stockRecommendationModel.historicStockData = it
                }
        }
    }
}