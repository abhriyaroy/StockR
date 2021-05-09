package studio.zebro.datasource.util

import android.util.Log
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import studio.zebro.datasource.model.ErrorModel
import studio.zebro.datasource.util.ErrorCodes.NETWORK_ERROR_CODE
import java.io.IOException

/**
 * A repository which provides resource from remote end point.
 *
 * [API_RESPONSE_TYPE] represents the type for network.
 * [MAPPED_RETURN_TYPE] represents the final type to be mapped into before returning.
 */
abstract class NetworkBoundSource<API_RESPONSE_TYPE, MAPPED_RETURN_TYPE> {

    /* @Inject
     lateinit var changePassWordModuleRoute: ChangePasswordRoute*/

    fun asFlow(gson: Gson) = flow<ResourceState<MAPPED_RETURN_TYPE>> {

        // Emit Loading State
        emit(ResourceState.loading())

        try {
            // Fetch latest data from server
            val apiResponse = fetchFromRemote()

            // Parse body
            val remoteData = apiResponse.body()

            // Check for response
            if (apiResponse.isSuccessful && remoteData != null) {
                // Emit success state with data
                emit(ResourceState.success(postProcess(remoteData)))
            } else {
                var errorResponse: ErrorModel? = null
                val adapter: TypeAdapter<ErrorModel> = gson.getAdapter(ErrorModel::class.java)
                try {
                    if (apiResponse.errorBody() != null) {
                        errorResponse = adapter.fromJson(
                            apiResponse?.errorBody()?.string()
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                // Emit Error state
                emit(ResourceState.error(errorResponse))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Emit Exception occurred
            emit(ResourceState.error(ErrorModel(NETWORK_ERROR_CODE, "Can't get latest data.")))
        }
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @WorkerThread
    protected abstract suspend fun fetchFromRemote(): Response<API_RESPONSE_TYPE>

    /**
     * Maps the [API_RESPONSE_TYPE] to a required [MAPPED_RETURN_TYPE].
     */
    @WorkerThread
    protected abstract suspend fun postProcess(originalData: API_RESPONSE_TYPE): MAPPED_RETURN_TYPE

}