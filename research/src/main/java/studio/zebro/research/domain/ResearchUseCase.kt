package studio.zebro.research.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import studio.zebro.research.data.HistoricalStockDataRepository
import studio.zebro.research.data.ResearchRepository
import studio.zebro.research.domain.mapper.HistoricStockDataModelMapper.mapHistoricalStockDataEntityToHistoricStockDataModel
import studio.zebro.research.domain.mapper.NiftyIndexesDayModelMapper.mapNiftyIndexesDayEntityToNiftyIndexesDayModel
import studio.zebro.research.domain.mapper.StockResearchModelMapper.mapStockResearchEntityToStockResearchModel
import studio.zebro.research.domain.model.HistoricStockDataModel
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.domain.model.StockResearchModel

interface ResearchUseCase {
    fun fetchResearch(isForceRefresh: Boolean = false): Flow<List<StockResearchModel>>
    fun fetchHistoricData(stockSymbol: String): Flow<HistoricStockDataModel>
    fun fetchNifty50Index(): Flow<NiftyIndexesDayModel>
}

internal class ResearchInteractor(
    private val researchRepository: ResearchRepository,
    private val historicalStockDataRepository: HistoricalStockDataRepository
) : ResearchUseCase {

    override fun fetchResearch(isForceRefresh: Boolean): Flow<List<StockResearchModel>> {
        return researchRepository.fetchStockResearch(isForceRefresh)
            .map {
                Log.d(this.javaClass.name, "---->>>>> here 1")
                it.map {
                    mapStockResearchEntityToStockResearchModel(it)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override fun fetchHistoricData(stockSymbol: String): Flow<HistoricStockDataModel> {
        return historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .map {
                mapHistoricalStockDataEntityToHistoricStockDataModel(it)
            }
    }

    override fun fetchNifty50Index(): Flow<NiftyIndexesDayModel> {
        return researchRepository.fetchNifty50Index()
            .map {
                mapNiftyIndexesDayEntityToNiftyIndexesDayModel(it)
            }
    }
}