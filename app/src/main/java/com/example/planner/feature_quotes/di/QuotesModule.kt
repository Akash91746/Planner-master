package com.example.planner.feature_quotes.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_quotes.data.data_source.remote.QuotesApi
import com.example.planner.feature_quotes.data.repository.local.LocalQuotesRepositoryImpl
import com.example.planner.feature_quotes.data.repository.remote.RemoteQuotesRepositoryImpl
import com.example.planner.feature_quotes.domain.repository.local.LocalQuotesRepository
import com.example.planner.feature_quotes.domain.repository.remote.RemoteQuotesRepository
import com.example.planner.feature_quotes.domain.useCases.GetQuote
import com.example.planner.feature_quotes.domain.useCases.QuotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object QuotesModule {

    private const val BASE_URL = "https://zenquotes.io"

    @Provides
    fun providesQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
    }

    @Provides
    fun providesQuotesRemoteRepository(
        quotesApi: QuotesApi,
    ): RemoteQuotesRepository {
        return RemoteQuotesRepositoryImpl(quotesApi)
    }

    @Provides
    fun providesQuotesLocalRepository(
        database: PlannerDatabase,
    ): LocalQuotesRepository {
        return LocalQuotesRepositoryImpl(database.quotesDao)
    }

    @Provides
    fun providesQuotesUseCases(
        remoteQuotesRepository: RemoteQuotesRepository,
        localQuotesRepository: LocalQuotesRepository,
    ): QuotesUseCases {

        return QuotesUseCases(
            getQuote = GetQuote(remoteQuotesRepository, localQuotesRepository)
        )
    }
}