package studio.zebro.datasource.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
}