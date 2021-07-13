package studio.zebro.core.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.core.util.DispatcherProvider
import studio.zebro.core.util.DispatcherProviderImpl
import studio.zebro.core.util.SerializerProvider
import studio.zebro.core.util.SerializerProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun providesSerializerProvider(gson: Gson) : SerializerProvider
    = SerializerProviderImpl(gson)

    @Provides
    @Singleton
    fun providesDispatcherProvider() : DispatcherProvider = DispatcherProviderImpl()
}