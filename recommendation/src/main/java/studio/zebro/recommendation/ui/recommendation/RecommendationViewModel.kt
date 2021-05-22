package studio.zebro.recommendation.ui.recommendation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.zebro.datasource.local.CustomError
import studio.zebro.datasource.model.ErrorModel
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.domain.RecommendationUseCase
import studio.zebro.recommendation.domain.model.HistoricStockDataModel
import studio.zebro.recommendation.domain.model.StockRecommendationModel

class RecommendationViewModel @ViewModelInject
constructor(private val recommendationUseCase: RecommendationUseCase) : ViewModel() {

    private val _stockRecommendations: MutableLiveData<ResourceState<List<StockRecommendationModel>>> =
        MutableLiveData()

    val stockRecommendations: LiveData<ResourceState<List<StockRecommendationModel>>> =
        _stockRecommendations

    private val _stockHistoricalData: MutableLiveData<ResourceState<HistoricStockDataModel>> =
        MutableLiveData()

    val stockHistoricalData: LiveData<ResourceState<HistoricStockDataModel>> = _stockHistoricalData

    fun getStockRecommendations(isForceRefresh: Boolean) {
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
                    Log.d(this.javaClass.name, "-------> just before $it")
                    _stockRecommendations.postValue(ResourceState.success(it))
                }
        }
    }

    fun getStockHistoricalData(stockRecommendationModel: StockRecommendationModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _stockHistoricalData.postValue(ResourceState.loading())
            recommendationUseCase.fetchHistoricData(stockRecommendationModel.shortName)
                .catch { error ->
                    if (error is CustomError) {
                        _stockHistoricalData.postValue(ResourceState.error(error.errorModel))
                    } else {
                        _stockHistoricalData.postValue(ResourceState.error(ErrorModel(message = error.message)))
                    }
                }
                .collect {
                    _stockHistoricalData.postValue(ResourceState.success(it))
                }
        }
    }

    fun clearStockHistoricalData(){
        _stockHistoricalData.postValue(ResourceState.loading())
    }
}