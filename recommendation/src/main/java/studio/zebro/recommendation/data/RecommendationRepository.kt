package studio.zebro.recommendation.data

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.model.StockRecommendationsDataModel
import studio.zebro.datasource.remote.RecommendationRemoteSource
import studio.zebro.datasource.util.NetworkBoundWithLocalSource
import studio.zebro.recommendation.data.entity.StockRecommendationEntity
import studio.zebro.recommendation.data.mapper.StockRecommendationEntityMapper.mapStockRecommendationRemoteDataModelToStockRecommendationEntity

class RecommendationRepository(
    private val gson: Gson,
    private val localPreferenceSource: LocalPreferenceSource,
    private val recommendationRemoteSource: RecommendationRemoteSource
) {

    fun fetchStockRecommendations(): Flow<List<StockRecommendationEntity>> = object :
        NetworkBoundWithLocalSource<List<StockRecommendationsDataModel>, List<StockRecommendationsDataModel>, List<StockRecommendationEntity>>() {
        override suspend fun saveToLocal(response: List<StockRecommendationsDataModel>) {
            localPreferenceSource.saveStockKotakRecommendation(response)
        }

        override suspend fun fetchFromLocal(): Flow<List<StockRecommendationsDataModel>> {
            return flow {
                localPreferenceSource.getSavedKotakStockRecommendation()
                    .let {
                        emit(it)
                    }
            }
        }

        override suspend fun fetchFromRemote(): Response<List<StockRecommendationsDataModel>> {
            return recommendationRemoteSource.getRecommendationsFromKotakSecurities()
        }

        override suspend fun postProcess(originalData: List<StockRecommendationsDataModel>): List<StockRecommendationEntity> {
            return originalData
                .map {
                    mapStockRecommendationRemoteDataModelToStockRecommendationEntity(it)
                }
        }

    }.asFlow(gson).flowOn(Dispatchers.IO)
}