package studio.zebro.datasource.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.local.LocalPreferenceSourceImpl
import studio.zebro.datasource.remote.RecommendationRemoteSource
import studio.zebro.datasource.remote.RecommendationRemoteSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesRecommendationRemoteSource(): RecommendationRemoteSource =
        RecommendationRemoteSourceImpl()

    @Singleton
    @Provides
    fun providesLocalPreferenceSource(gson: Gson): LocalPreferenceSource =
        LocalPreferenceSourceImpl(gson)

    @Singleton
    @Provides
    fun providesGsonSerializer(): Gson = Gson()
}