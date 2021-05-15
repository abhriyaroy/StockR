package studio.zebro.recommendation.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.data.HistoricalStockDataRepository
import studio.zebro.recommendation.data.RecommendationRepository
import studio.zebro.recommendation.domain.mapper.StockRecommendationModelMapper.mapStockRecommendationEntityToStockRecommendationModel
import studio.zebro.recommendation.domain.model.StockRecommendationModel

interface RecommendationUseCase {
    fun fetchRecommendations(): Flow<ResourceState<List<StockRecommendationModel>>>
}

internal class RecommendationsInteractor(
    private val recommendationRepository: RecommendationRepository,
    private val historicalStockDataRepository: HistoricalStockDataRepository
) : RecommendationUseCase {

    override fun fetchRecommendations(): Flow<ResourceState<List<StockRecommendationModel>>> =
        recommendationRepository.fetchStockRecommendations()
            .mapLatest {
                when (it) {
                    is ResourceState.Success -> {
                        ResourceState.success(it.data.map {
                            historicalStockDataRepository.getHistoricDataForStock(it.shortName)
                            mapStockRecommendationEntityToStockRecommendationModel(it)
                        })
                    }
                    is ResourceState.Error -> {
                        ResourceState.error(it.error)
                    }
                    is ResourceState.Loading -> {
                        ResourceState.loading()
                    }
                }
            }.flowOn(Dispatchers.IO)
}