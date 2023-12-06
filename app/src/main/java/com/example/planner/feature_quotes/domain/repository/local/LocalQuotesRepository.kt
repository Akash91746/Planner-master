package com.example.planner.feature_quotes.domain.repository.local

import com.example.planner.feature_quotes.domain.models.Quote
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LocalQuotesRepository {

    suspend fun insert(quote: Quote)

    suspend fun delete(quote: Quote)

    fun getQuotes() : Flow<List<Quote>>

    suspend fun getQuoteByDate(date: LocalDate) : Quote?

    suspend fun deletePreviousQuote(date: LocalDate)
}