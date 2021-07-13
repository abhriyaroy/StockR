package studio.zebro.core.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    fun getIoDispatcher() : CoroutineContext
    fun getMainDispatcher() : CoroutineContext
}

class DispatcherProviderImpl : DispatcherProvider{
    override fun getIoDispatcher(): CoroutineContext  = Dispatchers.IO
    override fun getMainDispatcher(): CoroutineContext  = Dispatchers.Main
}