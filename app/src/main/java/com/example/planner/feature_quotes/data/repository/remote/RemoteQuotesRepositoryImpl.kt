package com.example.planner.feature_quotes.data.repository.remote

import com.example.planner.feature_quotes.data.data_source.remote.QuotesApi
import com.example.planner.feature_quotes.data.dto.QuoteDto
import com.example.planner.feature_quotes.domain.repository.remote.RemoteQuotesRepository

class RemoteQuotesRepositoryImpl(
    private val quotesApi: QuotesApi
): RemoteQuotesRepository {

    override suspend fun getTodayQuote(): Result<QuoteDto> {
        val result = quotesApi.getTodayQuotes()

        val quote = result.body()

        if(!result.isSuccessful || quote == null){
            return Result.failure(Throwable(result.message()))
        }

        return Result.success(quote[0])
    }
}