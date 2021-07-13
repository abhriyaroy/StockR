package studio.zebro.research.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import studio.zebro.core.util.DispatcherProvider
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
import studio.zebro.datasource.util.NetworkBoundSource
import studio.zebro.research.data.entity.HistoricalStockDataEntity
import studio.zebro.research.data.mapper.HistoricalStockDataMapper

class HistoricalStockDataRepository(
    private val dispatcherProvider : DispatcherProvider,
    private val serializerProvider: SerializerProvider,
    private val localPreferenceSource: LocalPreferenceSource,
    private val historicalDataRemoteSource: HistoricalDataRemoteSource
) {

    fun getHistoricDataForStock(stockSymbol: String): Flow<HistoricalStockDataEntity> =
        object :
            NetworkBoundSource<List<HistoricalStockDataDayWiseModel>, HistoricalStockDataEntity>() {

            override suspend fun fetchFromRemote(): Response<List<HistoricalStockDataDayWiseModel>> {
                return historicalDataRemoteSource.get3monthsHistoricData(stockSymbol)
            }

            override suspend fun postProcess(originalData: List<HistoricalStockDataDayWiseModel>): HistoricalStockDataEntity {
                return HistoricalStockDataMapper.mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntity(
                    originalData
                )
            }

        }.asFlow(serializerProvider.getGson()).flowOn(dispatcherProvider.getIoDispatcher())

}