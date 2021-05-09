package studio.zebro.datasource.util

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import retrofit2.Response
import studio.zebro.datasource.model.ErrorModel
import studio.zebro.datasource.util.ErrorCodes.NETWORK_ERROR_CODE

/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [DB_TYPE] represents the type for database.
 * [API_RESPONSE_TYPE] represents the type for network.
 * [MAPPED_RETURN_TYPE] represents the final type to be mapped into before returning.
 */
abstract class NetworkBoundWithLocalSource<DB_TYPE, API_RESPONSE_TYPE, MAPPED_RETURN_TYPE> {

  fun asFlow(gson: Gson) = flow<ResourceState<MAPPED_RETURN_TYPE>> {

    try {
      // Emit Database content first
      fetchFromLocal().collect {
        emit(ResourceState.success(postProcess(it)))
      }

      // Emit Loading State
      emit(ResourceState.loading())

      // Fetch latest data from remote
      val apiResponse = fetchFromRemote()

      // Parse body
      val remotePosts = apiResponse.body()

      // Check for response
      if (apiResponse.isSuccessful && remotePosts != null) {
        // Save data into the storage
        saveToLocal(remotePosts)
      } else {

        val type = object : TypeToken<ErrorModel>() {}.type
        val errorResponse: ErrorModel? =
          gson.fromJson(apiResponse.errorBody()?.charStream(), type)

        // Emit Error state
        emit(ResourceState.error(errorResponse))
      }
    } catch (e: Exception) {
      e.printStackTrace()
      // Emit Exception occurred!
      emit(ResourceState.error(ErrorModel(NETWORK_ERROR_CODE, " Can't get latest data.")))
    }

    // Retrieve data from storage and emit
    emitAll(
      fetchFromLocal().map {
        ResourceState.success(postProcess(it))
      }
    )
  }

  /**
   * Saves data from remote into the storage.
   */
  @WorkerThread
  protected abstract suspend fun saveToLocal(response: API_RESPONSE_TYPE)

  /**
   * Retrieves all data from persistence storage.
   */
  @WorkerThread
  protected abstract suspend fun fetchFromLocal(): Flow<DB_TYPE>

  /**
   * Fetches [Response] from the remote end point.
   */
  @WorkerThread
  protected abstract suspend fun fetchFromRemote(): Response<API_RESPONSE_TYPE>

  /**
   * Maps the [API_RESPONSE_TYPE] to a required [MAPPED_RETURN_TYPE].
   */
  @WorkerThread
  protected abstract suspend fun postProcess(originalData: DB_TYPE): MAPPED_RETURN_TYPE
}