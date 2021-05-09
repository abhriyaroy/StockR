package studio.zebro.core.util

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import studio.zebro.datasource.util.ResourceState

abstract class RepositoryBoundSource<REPOSITORY_TYPE, MAPPED_RETURN_TYPE> {

    fun asFlow() : Flow<ResourceState<MAPPED_RETURN_TYPE>> = fetchFromRepository().map {
            when (it) {
                is ResourceState.Success -> {
                    ResourceState.success(postProcess(it.data))
                }
                is ResourceState.Error -> {
                    ResourceState.error(it.error)
                }
                is ResourceState.Loading -> {
                    ResourceState.loading()
                }
            }
        }

    /**
     * Fetches [Response] from the repository.
     */
    @WorkerThread
    protected abstract suspend fun fetchFromRepository(): Flow<ResourceState<REPOSITORY_TYPE>>

    /**
     * Maps the [REPOSITORY_TYPE] to a required [MAPPED_RETURN_TYPE].
     */
    @WorkerThread
    protected abstract suspend fun postProcess(originalData: REPOSITORY_TYPE): MAPPED_RETURN_TYPE
}