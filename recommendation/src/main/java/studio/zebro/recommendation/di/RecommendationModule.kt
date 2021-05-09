package studio.zebro.recommendation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.core.navigation.RecommendationModuleRoute
import studio.zebro.datasource.remote.RecommendationRemoteSource
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
    fun providesRecommendationRepository(recommendationRemoteSource: RecommendationRemoteSource)
    : RecommendationRepository = RecommendationRepository(recommendationRemoteSource)

    @Singleton
    @Provides
    fun providesRecommendationUseCase(recommendationRepository: RecommendationRepository)
            : RecommendationUseCase = RecommendationsInteractor(recommendationRepository)
}