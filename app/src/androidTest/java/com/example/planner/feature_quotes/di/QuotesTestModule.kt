package com.example.planner.feature_quotes.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_quotes.data.data_source.QuotesApiMock
import com.example.planner.feature_quotes.data.data_source.remote.QuotesApi
import com.example.planner.feature_quotes.data.repository.RemoteQuotesRepositoryMock
import com.example.planner.feature_quotes.data.repository.local.LocalQuotesRepositoryImpl
import com.example.planner.feature_quotes.domain.repository.local.LocalQuotesRepository
import com.example.planner.feature_quotes.domain.repository.remote.RemoteQuotesRepository
import com.example.planner.feature_quotes.domain.useCases.GetQuote
import com.example.planner.feature_quotes.domain.useCases.QuotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [QuotesModule::class]
)
object QuotesTestModule {

    @Provides
    fun providesQuoteApi(): QuotesApi {
        return QuotesApiMock()
    }

    @Provides
    fun providesRemoteQuotesRepository(): RemoteQuotesRepository{
        return RemoteQuotesRepositoryMock()
    }

    @Provides
    fun providesLocalQuotesRepository(
        database: PlannerDatabase
    ): LocalQuotesRepository {
        return LocalQuotesRepositoryImpl(database.quotesDao)
    }

    @Provides
    fun providesQuotesUseCases(
        localQuotesRepository: LocalQuotesRepository,
    ): QuotesUseCases {

        return QuotesUseCases(
            getQuote = GetQuote(RemoteQuotesRepositoryMock(), localQuotesRepository)
        )
    }

}