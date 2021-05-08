package studio.zebro.stockr.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.stockr.data.RecommendationRepository
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class RecommendationModule {
    @Provides
    @Singleton
    fun providesRecommendationRepository() :RecommendationRepository = RecommendationRepository()
}