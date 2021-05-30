package studio.zebro.recommendation.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import studio.zebro.recommendation.data.HistoricalStockDataRepository
import studio.zebro.recommendation.data.RecommendationRepository
import studio.zebro.recommendation.domain.mapper.HistoricStockDataModelMapper.mapHistoricalStockDataEntityToHistoricStockDataModel
import studio.zebro.recommendation.domain.mapper.StockRecommendationModelMapper.mapStockRecommendationEntityToStockRecommendationModel
import studio.zebro.recommendation.domain.model.HistoricStockDataModel
import studio.zebro.recommendation.domain.model.StockRecommendationModel

interface RecommendationUseCase {
    fun fetchRecommendations(isForceRefresh: Boolean = false): Flow<List<StockRecommendationModel>>
    fun fetchHistoricData(stockSymbol: String): Flow<HistoricStockDataModel>
}

internal class RecommendationsInteractor(
    private val recommendationRepository: RecommendationRepository,
    private val historicalStockDataRepository: HistoricalStockDataRepository
) : RecommendationUseCase {

    override fun fetchRecommendations(isForceRefresh: Boolean): Flow<List<StockRecommendationModel>> {
        return recommendationRepository.fetchStockRecommendations(isForceRefresh)
            .map {
                Log.d(this.javaClass.name, "---->>>>> here 1")
                it.map {
                    mapStockRecommendationEntityToStockRecommendationModel(it)
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
}