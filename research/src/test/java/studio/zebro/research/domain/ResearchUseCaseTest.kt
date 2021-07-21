package studio.zebro.research.domain

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import studio.zebro.core.util.DispatcherProvider
import studio.zebro.research.data.HistoricalStockDataRepository
import studio.zebro.research.data.ResearchRepository
import studio.zebro.research.domain.mapper.HistoricStockDataModelMapper
import studio.zebro.research.domain.mapper.HistoricStockDataModelMapperTestHelper.mapHistoricalStockDataEntityToHistoricStockDataModel
import studio.zebro.research.domain.mapper.NiftyIndexesDayModelMapperTestHelper.mapNiftyIndexesDayEntityToNiftyIndexesDayModel
import studio.zebro.research.domain.mapper.StockResearchModelMapper.mapStockResearchEntityToStockResearchModel
import studio.zebro.research.domain.mapper.StockResearchModelMapper.mapStockResearchModelToStockResearchEntity
import studio.zebro.research.testdataprovider.HistoricalStockDataProvider.getHistoricalStockDataDayWiseModelList
import studio.zebro.research.testdataprovider.HistoricalStockDataProvider.getHistoricalStockDataEntity
import studio.zebro.research.testdataprovider.NiftyIndexesDayWiseDataModelDataProvider.getNiftyIndexesDayEntity
import studio.zebro.research.testdataprovider.StockResearchDataModelProvider.getStockResearchEntityList
import java.lang.IllegalStateException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ResearchUseCaseTest {

    @Mock
    private lateinit var researchRepository : ResearchRepository
    @Mock
    private lateinit var historicalStockDataRepository : HistoricalStockDataRepository
    private lateinit var researchInteractor: ResearchInteractor

    @Before
    fun setup(){
        researchInteractor = ResearchInteractor(researchRepository, historicalStockDataRepository)
    }

    @Test
    fun `should return StockResearchModel list as flow on fetchResearch call success with isForceRefresh true`() = runBlocking {
        val researchList = getStockResearchEntityList()
        val mappedList = researchList
            .map {
                mapStockResearchEntityToStockResearchModel(it)
            }
        `when`(researchRepository.fetchStockResearch(true)).thenReturn(
            flow {
                emit(researchList)
            }
        )

        researchInteractor.fetchResearch(true)
            .collect {
                assertEquals(mappedList, it)
            }

        verify(researchRepository).fetchStockResearch(true)
        Unit
    }

    @Test
    fun `should return error on fetchResearch call failre due to fetchStockResearch error with isForceRefresh true`() = runBlocking {
        `when`(researchRepository.fetchStockResearch(true)).thenReturn(
            flow {
                throw IllegalStateException()
            }
        )
        var isExceptionCaught = false

        researchInteractor.fetchResearch(true)
            .catch { e ->
                assert(e is IllegalStateException)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(researchRepository).fetchStockResearch(true)
        Unit
    }

    @Test
    fun `should return StockResearchModel list as flow on fetchResearch call success with isForceRefresh false`() = runBlocking {
        val researchList = getStockResearchEntityList()
        val mappedList = researchList
            .map {
                mapStockResearchEntityToStockResearchModel(it)
            }
        `when`(researchRepository.fetchStockResearch(false)).thenReturn(
            flow {
                emit(researchList)
            }
        )

        researchInteractor.fetchResearch(false)
            .collect {
                assertEquals(mappedList, it)
            }

        verify(researchRepository).fetchStockResearch(false)
        Unit
    }

    @Test
    fun `should return error on fetchResearch call failre due to fetchStockResearch error with isForceRefresh false`() = runBlocking {
        `when`(researchRepository.fetchStockResearch(false)).thenReturn(
            flow {
                throw IllegalStateException()
            }
        )
        var isExceptionCaught = false

        researchInteractor.fetchResearch(false)
            .catch { e ->
                assert(e is IllegalStateException)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(researchRepository).fetchStockResearch(false)
        Unit
    }

    @Test
    fun `should return historic stock data as flow on fetchHistoricData call success`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        val researchList = getHistoricalStockDataEntity()
        val mappedList = mapHistoricalStockDataEntityToHistoricStockDataModel(researchList)
        `when`(historicalStockDataRepository.getHistoricDataForStock(stockSymbol)).thenReturn(
            flow {
                emit(researchList)
            }
        )

        researchInteractor.fetchHistoricData(stockSymbol)
            .collect {
                assertEquals(mappedList, it)
            }

        verify(historicalStockDataRepository).getHistoricDataForStock(stockSymbol)
        Unit
    }

    @Test
    fun `should return error as flow on fetchHistoricData call failure`() = runBlocking {
        val stockSymbol = UUID.randomUUID().toString()
        `when`(historicalStockDataRepository.getHistoricDataForStock(stockSymbol)).thenReturn(
            flow {
                throw IllegalStateException()
            }
        )
        var isExceptionCaught = false

        researchInteractor.fetchHistoricData(stockSymbol)
            .catch { e ->
                assert(e is IllegalStateException)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(historicalStockDataRepository).getHistoricDataForStock(stockSymbol)
        Unit
    }

    @Test
    fun `should return nifty 50 index entity on fetchNifty50Index call success`() = runBlocking {
        val nifty50Entity = getNiftyIndexesDayEntity()
        val mappedItem = mapNiftyIndexesDayEntityToNiftyIndexesDayModel(nifty50Entity)
        `when`(researchRepository.fetchNifty50Index()).thenReturn(
            flow {
                emit(nifty50Entity)
            }
        )

        researchInteractor.fetchNifty50Index()
            .collect {
                assertEquals(mappedItem, it)
            }

        verify(researchRepository).fetchNifty50Index()
        Unit
    }

    @Test
    fun `should return error on fetchNifty50Index call failure`() = runBlocking {
        `when`(researchRepository.fetchNifty50Index()).thenReturn(
            flow {
                throw IllegalStateException()
            }
        )
        var isExceptionCaught = false

        researchInteractor.fetchNifty50Index()
            .catch { e ->
                assertTrue(e is IllegalStateException)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(researchRepository).fetchNifty50Index()
        Unit
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(researchRepository, historicalStockDataRepository)
    }
}