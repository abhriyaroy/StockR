package studio.zebro.recommendation.data

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
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
import studio.zebro.recommendation.testdataprovider.NiftyIndexesDayWiseDataModelDataProvider
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

    @Before
    fun setup(){
        researchRepository = ResearchRepository(
            dispatcherProvider,
            serializerProvider,
            localPreferenceSource,
            researchRemoteSource
        )

        Mockito.`when`(serializerProvider.getGson()).thenReturn(gson)
        Mockito.`when`(dispatcherProvider.getIoDispatcher()).thenReturn(Dispatchers.Default)
    }

    @Test
    fun `should return Nifty50 index on fetchNifty50Index call success`()= runBlocking {
        val niftyIndexesDayWiseDataModel
        = NiftyIndexesDayWiseDataModelDataProvider.getNiftyIndexesDayWiseDataModel()
        val mappedNiftyIndexesDayEntity =
            mapNiftyIndexesDayWiseDataModelToNiftyIndexesDayEntity(
                niftyIndexesDayWiseDataModel
            )
        Mockito.`when`(researchRemoteSource.getNifty50Index()).thenReturn(
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

        researchRepository.fetchNifty50Index()
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
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
        Mockito.`when`(researchRemoteSource.getNifty50Index())
            .thenReturn(Response.error(Constants.ERROR_CODE_NOT_LOADED, errorResponseBody))

        researchRepository.fetchNifty50Index()
            .catch { e->
                assert(e is CustomError)
                assertEquals(ErrorCodes.NETWORK_ERROR_CODE, (e as CustomError).errorModel!!.code)
                assertEquals(ErrorCodes.NETWORK_ERROR_MESSAGE, (e as CustomError).errorModel!!.message)
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