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
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import retrofit2.Response
import studio.zebro.core.util.DispatcherProvider
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.CustomError
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.ResearchRemoteSource
import studio.zebro.datasource.util.Constants
import studio.zebro.datasource.util.ErrorCodes
import studio.zebro.research.data.mapper.StockResearchEntityMapperTestHelper
import studio.zebro.research.testdataprovider.NiftyIndexesDayWiseDataModelDataProvider
import studio.zebro.research.testdataprovider.StockResearchDataModelProvider.getStockResearchDataModelList
import studio.zebro.research.data.ResearchRepository
import studio.zebro.research.data.mapper.NiftyIndexesDayEntityMapper.mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity
import java.lang.IllegalStateException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ResearchRepositoryTest {

    @Mock
    lateinit var localPreferenceSource: LocalPreferenceSource
    @Mock
    lateinit var researchRemoteSource: ResearchRemoteSource
    @Mock
    private lateinit var serializerProvider: SerializerProvider
    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var researchRepository: ResearchRepository
    private val gson = Gson()
    private lateinit var inOrder : InOrder

    @Before
    fun setup(){
        researchRepository = ResearchRepository(
            dispatcherProvider,
            serializerProvider,
            localPreferenceSource,
            researchRemoteSource
        )

        `when`(serializerProvider.getGson()).thenReturn(gson)
        `when`(dispatcherProvider.getIoDispatcher()).thenReturn(Dispatchers.Default)

        inOrder = inOrder(localPreferenceSource, researchRemoteSource)
    }

    @Test
    fun `should return new list of stock research on fetchStockResearch call success with isForceRefresh true`()
    = runBlocking {
        val listOfStockResearchDataModel = getStockResearchDataModelList()
        val mappedList = listOfStockResearchDataModel.map {
            StockResearchEntityMapperTestHelper.mapStockResearchRemoteDataModelToStockResearchEntity(it)
        }
        `when`(researchRemoteSource.getResearchFromKotakSecurities()).thenReturn(
            Response.success(listOfStockResearchDataModel)
        )

        researchRepository.fetchStockResearch(true)
            .collect {
                assertEquals(mappedList, it)
            }

        verify(serializerProvider).getGson()
        verify(dispatcherProvider).getIoDispatcher()
        inOrder.verify(researchRemoteSource).getResearchFromKotakSecurities()
        inOrder.verify(localPreferenceSource).saveStockKotakResearch(listOfStockResearchDataModel)
    }

    @Test
    fun `should return exception on fetchStockResearch call failure due to getResearchFromKotakSecurities error response with isForceRefresh true`()
            = runBlocking {
        val errorResponseBody = ResponseBody.create(null, "{}")
        `when`(researchRemoteSource.getResearchFromKotakSecurities()).thenReturn(
            Response.error(Constants.ERROR_CODE_NOT_LOADED, errorResponseBody)
        )
        var isExceptionCaught = false

        researchRepository.fetchStockResearch(true)
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(serializerProvider).getGson()
        verify(researchRemoteSource).getResearchFromKotakSecurities()
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return exception on fetchStockResearch call failure due to getResearchFromKotakSecurities throwing exception with isForceRefresh true`()
            = runBlocking {
        `when`(researchRemoteSource.getResearchFromKotakSecurities()).thenThrow(IllegalStateException::class.java)
        var isExceptionCaught = false

        researchRepository.fetchStockResearch(true)
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(serializerProvider).getGson()
        verify(researchRemoteSource).getResearchFromKotakSecurities()
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return new list of stock research on fetchStockResearch call success with isForceRefresh false`()
            = runBlocking {
        val listOfStockResearchDataModel = getStockResearchDataModelList()
        val listOfCachedStockResearchDataModel = getStockResearchDataModelList()
        val mappedList = listOfStockResearchDataModel.map {
            StockResearchEntityMapperTestHelper.mapStockResearchRemoteDataModelToStockResearchEntity(it)
        }
        val mappedCacheList = listOfCachedStockResearchDataModel.map {
            StockResearchEntityMapperTestHelper.mapStockResearchRemoteDataModelToStockResearchEntity(it)
        }
        `when`(researchRemoteSource.getResearchFromKotakSecurities()).thenReturn(
            Response.success(listOfStockResearchDataModel)
        )
        `when`(localPreferenceSource.getSavedKotakStockResearch()).thenReturn(listOfCachedStockResearchDataModel)
        var isCacheValueComing = true

        researchRepository.fetchStockResearch(false)
            .collect {
                if(isCacheValueComing) {
                    isCacheValueComing = false
                    assertEquals(mappedCacheList, it)
                } else {
                    assertEquals(mappedList, it)
                }
            }

        verify(serializerProvider).getGson()
        verify(dispatcherProvider).getIoDispatcher()
        inOrder.verify(localPreferenceSource).getSavedKotakStockResearch()
        inOrder.verify(researchRemoteSource).getResearchFromKotakSecurities()
        inOrder.verify(localPreferenceSource).saveStockKotakResearch(listOfStockResearchDataModel)
    }

    @Test
    fun `should return exception on fetchStockResearch call failure due to getSavedKotakStockResearch throws exception with isForceRefresh false`()
            = runBlocking {
        `when`(localPreferenceSource.getSavedKotakStockResearch()).thenThrow(IllegalStateException::class.java)
        var isExceptionCaught = false

        researchRepository.fetchStockResearch(false)
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(serializerProvider).getGson()
        verify(dispatcherProvider).getIoDispatcher()
        inOrder.verify(localPreferenceSource).getSavedKotakStockResearch()
        Unit
    }

    @Test
    fun `should return exception on fetchStockResearch call failure due to getResearchFromKotakSecurities throws exception with isForceRefresh false`()
            = runBlocking {
        val listOfCachedStockResearchDataModel = getStockResearchDataModelList()
        val mappedCacheList = listOfCachedStockResearchDataModel.map {
            StockResearchEntityMapperTestHelper.mapStockResearchRemoteDataModelToStockResearchEntity(it)
        }
        `when`(localPreferenceSource.getSavedKotakStockResearch()).thenReturn(listOfCachedStockResearchDataModel)
        `when`(researchRemoteSource.getResearchFromKotakSecurities()).thenThrow(IllegalStateException::class.java)
        var isExceptionCaught = false

        researchRepository.fetchStockResearch(false)
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(serializerProvider).getGson()
        verify(dispatcherProvider).getIoDispatcher()
        inOrder.verify(localPreferenceSource).getSavedKotakStockResearch()
        inOrder.verify(researchRemoteSource).getResearchFromKotakSecurities()
        Unit
    }

    @Test
    fun `should return exception on fetchStockResearch call failure due to getResearchFromKotakSecurities return error response with isForceRefresh false`()
            = runBlocking {
        val listOfCachedStockResearchDataModel = getStockResearchDataModelList()
        val mappedCacheList = listOfCachedStockResearchDataModel.map {
            StockResearchEntityMapperTestHelper.mapStockResearchRemoteDataModelToStockResearchEntity(it)
        }
        val errorResponseBody = ResponseBody.create(null, "{}")
        `when`(localPreferenceSource.getSavedKotakStockResearch()).thenReturn(listOfCachedStockResearchDataModel)
        `when`(researchRemoteSource.getResearchFromKotakSecurities())
            .thenReturn(Response.error(Constants.ERROR_CODE_NOT_LOADED, errorResponseBody))
        var isExceptionCaught = false

        researchRepository.fetchStockResearch(false)
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {}

        verify(serializerProvider).getGson()
        verify(dispatcherProvider).getIoDispatcher()
        inOrder.verify(localPreferenceSource).getSavedKotakStockResearch()
        inOrder.verify(researchRemoteSource).getResearchFromKotakSecurities()
        Unit
    }

    /**
     * ---------------------------------------------------------------------------------------
     */

    @Test
    fun `should return Nifty50 index on fetchNifty50Index call success`()= runBlocking {
        val niftyIndexesDayWiseDataModel
        = NiftyIndexesDayWiseDataModelDataProvider.getNiftyIndexesDayWiseDataModel()
        val mappedNiftyIndexesDayEntity =
            mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity(
                niftyIndexesDayWiseDataModel
            )
        `when`(researchRemoteSource.getNifty50Index()).thenReturn(
            Response.success(niftyIndexesDayWiseDataModel)
        )

        researchRepository.fetchNifty50Index()
            .collect {
                assertEquals(mappedNiftyIndexesDayEntity, it)
            }
        verify(serializerProvider).getGson()
        verify(researchRemoteSource).getNifty50Index()
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return exception on fetchNifty50Index failure due to getNifty50Index call failure with IllegalStateException`() = runBlocking {
        Mockito.`when`(researchRemoteSource.getNifty50Index())
            .thenThrow(IllegalStateException::class.java)
        var isExceptionCaught = false

        researchRepository.fetchNifty50Index()
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {  }
        verify(serializerProvider).getGson()
        verify(researchRemoteSource).getNifty50Index()
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @Test
    fun `should return exception on fetchNifty50Index failure due to getNifty50Index call failure with Error Response`() = runBlocking {
        val errorResponseBody = ResponseBody.create(null, "{}")
        `when`(researchRemoteSource.getNifty50Index())
            .thenReturn(Response.error(Constants.ERROR_CODE_NOT_LOADED, errorResponseBody))
        var isExceptionCaught = false

        researchRepository.fetchNifty50Index()
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
                isExceptionCaught = true
            }
            .onCompletion {
                assertTrue(isExceptionCaught)
            }
            .collect {  }
        verify(serializerProvider).getGson()
        verify(researchRemoteSource).getNifty50Index()
        verify(dispatcherProvider).getIoDispatcher()
        Unit
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(serializerProvider, localPreferenceSource, researchRemoteSource)
    }

}