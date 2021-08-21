package studio.zebro.stockr.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import studio.zebro.stockr.data.ResearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ResearchModule {
    @Provides
    @Singleton
    fun providesResearchRepository() :ResearchRepository = ResearchRepository()
}