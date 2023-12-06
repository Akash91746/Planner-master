package com.example.planner.feature_quotes.domain.repository.remote

import com.example.planner.feature_quotes.data.dto.QuoteDto

interface RemoteQuotesRepository {

    suspend fun getTodayQuote() : Result<QuoteDto>

}