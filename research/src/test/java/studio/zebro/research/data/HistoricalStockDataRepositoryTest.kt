package studio.zebro.research.data

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
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
import studio.zebro.core.util.DispatcherProvider
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.CustomError
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
import studio.zebro.datasource.util.Constants.ERROR_CODE_NOT_LOADED
import studio.zebro.datasource.util.ErrorCodes.NETWORK_ERROR_CODE
import studio.zebro.datasource.util.ErrorCodes.NETWORK_ERROR_MESSAGE
import studio.zebro.research.data.mapper.HistoricalStockDataMapperTestHelper.mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntityTest
import studio.zebro.research.testdataprovider.HistoricalStockDataProvider.getHistoricalStockDataDayWiseModelList
import studio.zebro.research.data.HistoricalStockDataRepository
import java.lang.IllegalStateException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class HistoricalStockDataRepositoryTest {

    @Mock
    lateinit var localPreferenceSource: LocalPreferenceSource
    @Mock
    lateinit var historicalDataRemoteSource: HistoricalDataRemoteSource
    @Mock
    private lateinit var serializerProvider: SerializerProvider
    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var historicalStockDataRepository: HistoricalStockDataRepository
    private val gson = Gson()

    @Before
    fun setup(){
        historicalStockDataRepository = HistoricalStockDataRepository(
            dispatcherProvider,
            serializerProvider,
            localPreferenceSource,
            historicalDataRemoteSource
        )

        `when`(serializerProvider.getGson()).thenReturn(gson)
        `when`(dispatcherProvider.getIoDispatcher()).thenReturn(Dispatchers.Default)
    }

    @Test
    fun `should return list of historical stock on getHistoricDataForStock success`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        val historicalStockDataDayWiseModelDataList = getHistoricalStockDataDayWiseModelList()
        val mappedHistoricalStockDataEntity = mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntityTest(historicalStockDataDayWiseModelDataList)
        `when`(historicalDataRemoteSource.get3monthsHistoricData(stockSymbol)).thenReturn(
            Response.success(historicalStockDataDayWiseModelDataList)
        )

        historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .collect {
                assertEquals(mappedHistoricalStockDataEntity, it)
            }
        verify(serializerProvider).getGson()
        verify(historicalDataRemoteSource).get3monthsHistoricData(stockSymbol)
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return exception on getHistoricDataForStock failure due to get3monthsHistoricData call failure with IllegalStateException`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        `when`(historicalDataRemoteSource.get3monthsHistoricData(stockSymbol)).thenThrow(IllegalStateException::class.java)
        var isExceptionCaught = false

        historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .catch { e->
                assert(e is CustomError)
                assertEquals(NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect { }
        verify(serializerProvider).getGson()
        verify(historicalDataRemoteSource).get3monthsHistoricData(stockSymbol)
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return exception on getHistoricDataForStock failure due to get3monthsHistoricData call failure with Error Response`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        val errorResponseBody = ResponseBody.create(null, "{}")
        `when`(historicalDataRemoteSource.get3monthsHistoricData(stockSymbol))
            .thenReturn(Response.error(ERROR_CODE_NOT_LOADED, errorResponseBody))
        var isExceptionCaught = false

        historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .catch { e->
                assert(e is CustomError)
                assertEquals(NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect { }
        verify(serializerProvider).getGson()
        verify(historicalDataRemoteSource).get3monthsHistoricData(stockSymbol)
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(serializerProvider, localPreferenceSource, historicalDataRemoteSource)
    }

}