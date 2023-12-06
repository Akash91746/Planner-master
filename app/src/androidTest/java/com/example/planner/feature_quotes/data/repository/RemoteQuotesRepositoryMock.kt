package com.example.planner.feature_quotes.data.repository

import com.example.planner.feature_quotes.data.dto.QuoteDto
import com.example.planner.feature_quotes.domain.repository.remote.RemoteQuotesRepository

class RemoteQuotesRepositoryMock : RemoteQuotesRepository {

    override suspend fun getTodayQuote(): Result<QuoteDto> {
        return Result.success(QuoteDto(
            q = "Some quote of the day",
            a = "test",
            h = "some text"
        ))
    }
}