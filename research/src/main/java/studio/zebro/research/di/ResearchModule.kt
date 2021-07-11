package studio.zebro.research.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import studio.zebro.core.navigation.ResearchModuleRoute
import studio.zebro.core.util.SerializerProvider
import studio.zebro.datasource.local.LocalPreferenceSource
import studio.zebro.datasource.remote.HistoricalDataRemoteSource
import studio.zebro.datasource.remote.ResearchRemoteSource
import studio.zebro.research.data.HistoricalStockDataRepository
import studio.zebro.research.data.ResearchRepository
import studio.zebro.research.domain.ResearchUseCase
import studio.zebro.research.domain.ResearchInteractor
import studio.zebro.research.navigation.ResearchModuleRouteImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ResearchModule {

    @Singleton
    @Provides
    fun providesResearchModuleRoute(): ResearchModuleRoute =
        ResearchModuleRouteImpl()

    @Singleton
    @Provides
    fun providesResearchRepository(
        gson: Gson,
        localPreferenceSource: LocalPreferenceSource,
        researchRemoteSource: ResearchRemoteSource
    ): ResearchRepository =
        ResearchRepository(gson, localPreferenceSource, researchRemoteSource)

    @Singleton
    @Provides
    fun providesHistoricStockDataRepository(
        serializerProvider: SerializerProvider,
        localPreferenceSource: LocalPreferenceSource,
        historicalDataRemoteSource: HistoricalDataRemoteSource
    ): HistoricalStockDataRepository =
        HistoricalStockDataRepository(serializerProvider, localPreferenceSource, historicalDataRemoteSource)

    @Singleton
    @Provides
    fun providesResearchUseCase(
        researchRepository: ResearchRepository,
        historicalStockDataRepository: HistoricalStockDataRepository
    ): ResearchUseCase =
        ResearchInteractor(researchRepository, historicalStockDataRepository)
}