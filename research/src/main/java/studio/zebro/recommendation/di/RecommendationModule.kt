package studio.zebro.recommendation.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
import studio.zebro.datasource.remote.RecommendationRemoteSource
import studio.zebro.recommendation.data.HistoricalStockDataRepository
import studio.zebro.recommendation.data.RecommendationRepository
import studio.zebro.recommendation.domain.RecommendationUseCase
import studio.zebro.recommendation.domain.RecommendationsInteractor
import studio.zebro.recommendation.navigation.RecommendationModuleRouteImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RecommendationModule {

    @Singleton
    @Provides
    fun providesRecommendationModuleRoute(): RecommendationModuleRoute =
        RecommendationModuleRouteImpl()

    @Singleton
    @Provides
    fun providesRecommendationRepository(
        gson: Gson,
        localPreferenceSource: LocalPreferenceSource,
        recommendationRemoteSource: RecommendationRemoteSource
    ): RecommendationRepository =
        RecommendationRepository(gson, localPreferenceSource, recommendationRemoteSource)

    @Singleton
    @Provides
    fun providesHistoricStockDataRepository(
        gson: Gson,
        localPreferenceSource: LocalPreferenceSource,
        historicalDataRemoteSource: HistoricalDataRemoteSource
    ): HistoricalStockDataRepository =
        HistoricalStockDataRepository(gson, localPreferenceSource, historicalDataRemoteSource)

    @Singleton
    @Provides
    fun providesRecommendationUseCase(
        recommendationRepository: RecommendationRepository,
        historicalStockDataRepository: HistoricalStockDataRepository
    ): RecommendationUseCase =
        RecommendationsInteractor(recommendationRepository, historicalStockDataRepository)
}