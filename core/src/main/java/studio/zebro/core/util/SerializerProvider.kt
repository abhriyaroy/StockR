package studio.zebro.core.util

import com.google.gson.Gson

interface SerializerProvider {
    fun getGson() : Gson
}

class SerializerProviderImpl(private val gson: Gson) : SerializerProvider{
    override fun getGson(): Gson {
        return gson
    }
}