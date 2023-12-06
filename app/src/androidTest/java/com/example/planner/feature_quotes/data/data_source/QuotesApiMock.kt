package com.example.planner.feature_quotes.data.data_source

import com.example.planner.feature_quotes.data.data_source.remote.QuotesApi
import com.example.planner.feature_quotes.data.dto.QuoteDto
import retrofit2.Response

class QuotesApiMock: QuotesApi {
    override suspend fun getTodayQuotes(): Response<List<QuoteDto>> {
        return Response.success(arrayListOf())
    }
}