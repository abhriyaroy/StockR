package studio.zebro.research.data

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.model.NiftyIndexesDayWiseDataModel
import studio.zebro.datasource.model.StockResearchsDataModel
import studio.zebro.datasource.remote.ResearchRemoteSource
import studio.zebro.datasource.util.NetworkBoundSource
import studio.zebro.datasource.util.NetworkBoundWithLocalSource
import studio.zebro.research.data.entity.NiftyIndexesDayEntity
import studio.zebro.research.data.entity.StockResearchEntity
import studio.zebro.research.data.mapper.NiftyIndexesDayEntityMapper.mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity
import studio.zebro.research.data.mapper.StockResearchEntityMapper.mapStockResearchRemoteDataModelToStockResearchEntity

class ResearchRepository(
    private val gson: Gson,
    private val localPreferenceSource: LocalPreferenceSource,
    private val researchRemoteSource: ResearchRemoteSource
) {

    fun fetchStockResearchs(isForceRefresh: Boolean = false): Flow<List<StockResearchEntity>> =
        if (isForceRefresh) {
            getStockResearchsFromRemoteAndSaveToCache()
        } else {
            getCachedResearchsAndLazilyUpdateCache()
        }

    fun fetchNifty50Index(): Flow<NiftyIndexesDayEntity> = object :
        NetworkBoundSource<NiftyIndexesDayWiseDataModel, NiftyIndexesDayEntity>() {
        override suspend fun fetchFromRemote(): Response<NiftyIndexesDayWiseDataModel> {
            return researchRemoteSource.getNifty50Index()
        }

        override suspend fun postProcess(originalData: NiftyIndexesDayWiseDataModel): NiftyIndexesDayEntity {
            return mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity(originalData)
        }
    }.asFlow(gson).flowOn(Dispatchers.IO)

    private fun getStockResearchsFromRemoteAndSaveToCache() = object :
        NetworkBoundSource<List<StockResearchsDataModel>, List<StockResearchEntity>>() {

        override suspend fun fetchFromRemote(): Response<List<StockResearchsDataModel>> {
            return researchRemoteSource.getResearchsFromKotakSecurities()
        }

        override suspend fun postProcess(originalData: List<StockResearchsDataModel>): List<StockResearchEntity> {
            localPreferenceSource.saveStockKotakResearch(originalData)
            return originalData
                .map {
                    mapStockResearchRemoteDataModelToStockResearchEntity(it)
                }
        }
    }.asFlow(gson).flowOn(Dispatchers.IO)

    private fun getCachedResearchsAndLazilyUpdateCache() = object :
        NetworkBoundWithLocalSource<List<StockResearchsDataModel>,
                List<StockResearchsDataModel>, List<StockResearchEntity>>() {

        override suspend fun saveToLocal(response: List<StockResearchsDataModel>) {
            localPreferenceSource.saveStockKotakResearch(response)
        }

        override suspend fun fetchFromLocal(): Flow<List<StockResearchsDataModel>> {
            return flow {
                localPreferenceSource.getSavedKotakStockResearch()
                    .let {
                        emit(it)
                    }
            }
        }

        override suspend fun fetchFromRemote(): Response<List<StockResearchsDataModel>> {
            return researchRemoteSource.getResearchsFromKotakSecurities()
        }

        override suspend fun postProcess(originalData: List<StockResearchsDataModel>): List<StockResearchEntity> {
            return originalData
                .map {
                    mapStockResearchRemoteDataModelToStockResearchEntity(it)
                }
        }
    }.asFlow(gson).flowOn(Dispatchers.IO)
}