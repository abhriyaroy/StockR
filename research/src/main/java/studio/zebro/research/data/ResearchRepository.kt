package studio.zebro.research.data

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.model.NiftyIndexesDayWiseDataModel
import studio.zebro.datasource.model.StockResearchDataModel
import studio.zebro.datasource.remote.ResearchRemoteSource
import studio.zebro.datasource.util.NetworkBoundSource
import studio.zebro.datasource.util.NetworkBoundWithLocalSource
import studio.zebro.research.data.entity.NiftyIndexesDayEntity
import studio.zebro.research.data.entity.StockResearchEntity
import studio.zebro.research.data.mapper.NiftyIndexesDayEntityMapper.mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity
import studio.zebro.research.data.mapper.StockResearchEntityMapper.mapStockResearchRemoteDataModelToStockResearchEntity

class ResearchRepository(
    private val serializerProvider: SerializerProvider,
    private val localPreferenceSource: LocalPreferenceSource,
    private val researchRemoteSource: ResearchRemoteSource
) {

    fun fetchStockResearch(isForceRefresh: Boolean = false): Flow<List<StockResearchEntity>> =
        if (isForceRefresh) {
            getStockResearchFromRemoteAndSaveToCache()
        } else {
            getCachedResearchAndLazilyUpdateCache()
        }

    fun fetchNifty50Index(): Flow<NiftyIndexesDayEntity> = object :
        NetworkBoundSource<NiftyIndexesDayWiseDataModel, NiftyIndexesDayEntity>() {
        override suspend fun fetchFromRemote(): Response<NiftyIndexesDayWiseDataModel> {
            return researchRemoteSource.getNifty50Index()
        }

        override suspend fun postProcess(originalData: NiftyIndexesDayWiseDataModel): NiftyIndexesDayEntity {
            return mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity(originalData)
        }
    }.asFlow(serializerProvider.getGson()).flowOn(Dispatchers.IO)

    private fun getStockResearchFromRemoteAndSaveToCache() = object :
        NetworkBoundSource<List<StockResearchDataModel>, List<StockResearchEntity>>() {

        override suspend fun fetchFromRemote(): Response<List<StockResearchDataModel>> {
            return researchRemoteSource.getResearchFromKotakSecurities()
        }

        override suspend fun postProcess(originalData: List<StockResearchDataModel>): List<StockResearchEntity> {
            localPreferenceSource.saveStockKotakResearch(originalData)
            return originalData
                .map {
                    mapStockResearchRemoteDataModelToStockResearchEntity(it)
                }
        }
    }.asFlow(serializerProvider.getGson()).flowOn(Dispatchers.IO)

    private fun getCachedResearchAndLazilyUpdateCache() = object :
        NetworkBoundWithLocalSource<List<StockResearchDataModel>,
                List<StockResearchDataModel>, List<StockResearchEntity>>() {

        override suspend fun saveToLocal(response: List<StockResearchDataModel>) {
            localPreferenceSource.saveStockKotakResearch(response)
        }

        override suspend fun fetchFromLocal(): Flow<List<StockResearchDataModel>> {
            return flow {
                localPreferenceSource.getSavedKotakStockResearch()
                    .let {
                        emit(it)
                    }
            }
        }

        override suspend fun fetchFromRemote(): Response<List<StockResearchDataModel>> {
            return researchRemoteSource.getResearchFromKotakSecurities()
        }

        override suspend fun postProcess(originalData: List<StockResearchDataModel>): List<StockResearchEntity> {
            return originalData
                .map {
                    mapStockResearchRemoteDataModelToStockResearchEntity(it)
                }
        }
    }.asFlow(serializerProvider.getGson()).flowOn(Dispatchers.IO)
}