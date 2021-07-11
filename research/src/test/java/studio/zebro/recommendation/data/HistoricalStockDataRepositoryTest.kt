package studio.zebro.recommendation.data

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import retrofit2.Response
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
import studio.zebro.recommendation.data.mapper.HistoricalStockDataMapperTest.mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntityTest
import studio.zebro.recommendation.testdataprovider.HistoricalStockDataProvider.getHistoricalStockDataDayWiseModelList
import studio.zebro.research.data.HistoricalStockDataRepository
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class HistoricalStockDataRepositoryTest {

    @Mock
    lateinit var localPreferenceSource: LocalPreferenceSource
    @Mock
    lateinit var historicalDataRemoteSource: HistoricalDataRemoteSource
    @Mock
    private lateinit var serializerProvider: SerializerProvider
    private lateinit var historicalStockDataRepository: HistoricalStockDataRepository
    private val gson = Gson()

    @Before
    fun setup(){
        historicalStockDataRepository = HistoricalStockDataRepository(
            serializerProvider,
            localPreferenceSource,
            historicalDataRemoteSource
        )
    }

    @Test
    fun `should return list of historical stock on getHistoricDataForStock success`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        val historicalStockDataDayWiseModelDataList = getHistoricalStockDataDayWiseModelList()
        val mappedHistoricalStockDataEntity = mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntityTest(historicalStockDataDayWiseModelDataList)
        `when`(historicalDataRemoteSource.get3monthsHistoricData(stockSymbol)).thenReturn(
            Response.success(historicalStockDataDayWiseModelDataList)
        )
        `when`(serializerProvider.getGson()).thenReturn(gson)

        historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .collect {
                assertEquals(mappedHistoricalStockDataEntity, it)
                verify(serializerProvider).getGson()
                verify(historicalDataRemoteSource).get3monthsHistoricData(stockSymbol)
            }
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(serializerProvider, localPreferenceSource, historicalDataRemoteSource)
    }

}