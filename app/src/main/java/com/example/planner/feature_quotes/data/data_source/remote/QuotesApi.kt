package com.example.planner.feature_quotes.data.data_source.remote

import com.example.planner.feature_quotes.data.dto.QuoteDto
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {

    @GET("/api/today")
    suspend fun getTodayQuotes(): Response<List<QuoteDto>>
}