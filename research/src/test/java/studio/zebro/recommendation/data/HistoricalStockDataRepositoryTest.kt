package studio.zebro.recommendation.data

import com.google.gson.Gson
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verifyNoMoreInteractions
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
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

    @Before
    fun setup(){
        historicalStockDataRepository = HistoricalStockDataRepository(
            serializerProvider,
            localPreferenceSource,
            historicalDataRemoteSource
        )
    }

    @Test
    fun `should return list of historical stock on getHistoricDataForStock success`(){
        val stockSymbol = UUID.randomUUID().toString()
        `when`(historicalDataRemoteSource.get3monthsHistoricData(stockSymbol)).thenReturn()
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(serializerProvider, localPreferenceSource, historicalDataRemoteSource)
    }

}