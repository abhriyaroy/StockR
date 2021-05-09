package studio.zebro.recommendation.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.zebro.datasource.util.ResourceState
import studio.zebro.recommendation.data.RecommendationRepository
import studio.zebro.recommendation.domain.mapper.StockRecommendationModelMapper.mapStockRecommendationEntityToStockRecommendationModel
import studio.zebro.recommendation.domain.model.StockRecommendationModel

interface RecommendationUseCase {
    fun fetchRecommendations(): Flow<ResourceState<List<StockRecommendationModel>>>
}

internal class RecommendationsInteractor(
    private val recommendationRepository: RecommendationRepository
) : RecommendationUseCase {

    override fun fetchRecommendations(): Flow<ResourceState<List<StockRecommendationModel>>> =
        recommendationRepository.fetchStockRecommendations().map {
            when (it) {
                is ResourceState.Success -> {
                    ResourceState.success(it.data.map {
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
        }
}