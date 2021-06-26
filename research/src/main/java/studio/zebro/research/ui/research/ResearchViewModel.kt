package studio.zebro.research.ui.research

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
import studio.zebro.research.domain.ResearchUseCase
import studio.zebro.research.domain.model.HistoricStockDataModel
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.domain.model.StockResearchModel

class ResearchViewModel @ViewModelInject
constructor(private val researchUseCase: ResearchUseCase) : ViewModel() {

    private val _stockResearchs: MutableLiveData<ResourceState<List<StockResearchModel>>> =
        MutableLiveData()
    val stockResearchs: LiveData<ResourceState<List<StockResearchModel>>> =
        _stockResearchs

    private val _stockHistoricalData: MutableLiveData<ResourceState<HistoricStockDataModel>> =
        MutableLiveData()
    val stockHistoricalData: LiveData<ResourceState<HistoricStockDataModel>> = _stockHistoricalData

    private val _nifty50IndexData: MutableLiveData<ResourceState<NiftyIndexesDayModel>> =
        MutableLiveData()
    val nifty50IndexData: LiveData<ResourceState<NiftyIndexesDayModel>> = _nifty50IndexData

    fun getStockResearchs(isForceRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _stockResearchs.postValue(ResourceState.loading())
            researchUseCase.fetchResearchs(isForceRefresh)
                .catch { error ->
                    if (error is CustomError) {
                        _stockResearchs.postValue(ResourceState.error(error.errorModel))
                    } else {
                        _stockResearchs.postValue(ResourceState.error(ErrorModel(message = error.message)))
                    }
                }
                .collect {
                    Log.d(this.javaClass.name, "-------> just before $it")
                    _stockResearchs.postValue(ResourceState.success(it))
                }
        }
    }

    fun getStockHistoricalData(stockResearchModel: StockResearchModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _stockHistoricalData.postValue(ResourceState.loading())
            researchUseCase.fetchHistoricData(stockResearchModel.shortName)
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

    fun getNifty50IndexData(){
        viewModelScope.launch(Dispatchers.IO) {
            _nifty50IndexData.postValue(ResourceState.loading())
            researchUseCase.fetchNifty50Index()
                .catch { error ->
                    if (error is CustomError) {
                        _nifty50IndexData.postValue(ResourceState.error(error.errorModel))
                    } else {
                        _nifty50IndexData.postValue(ResourceState.error(ErrorModel(message = error.message)))
                    }
                }
                .collect {
                    _nifty50IndexData.postValue(ResourceState.success(it))
                }
        }
    }
}