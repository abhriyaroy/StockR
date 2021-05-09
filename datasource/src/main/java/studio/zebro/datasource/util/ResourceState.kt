package studio.zebro.datasource.util

import studio.zebro.datasource.model.ErrorModel

/**
 * Data and UI State management
 */
sealed class ResourceState<T> {


  class Loading<T> : ResourceState<T>()

  data class Success<T>(val data: T) : ResourceState<T>()

  data class Error<T>(val error: ErrorModel?) :
    ResourceState<T>()

  companion object {

    /**
     * Returns [ResourceState.Loading] instance.
     */
    fun <T> loading() =
      Loading<T>()

    /**
     * Returns [ResourceState.Success] instance.
     * @param data Data to emit with status.
     */
    fun <T> success(data: T) =
      Success(data)

    /**
     * Returns [ResourceState.Error] instance.
     * @param message Description of failure.
     */
    fun <T> error(error: ErrorModel?) = Error<T>(error)
  }

}