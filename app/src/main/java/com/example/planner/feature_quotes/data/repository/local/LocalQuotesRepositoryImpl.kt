package com.example.planner.feature_quotes.data.repository.local

import com.example.planner.feature_quotes.data.data_source.local.QuotesDao
import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_quotes.domain.repository.local.LocalQuotesRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LocalQuotesRepositoryImpl(
    private val dao: QuotesDao
): LocalQuotesRepository {

    override suspend fun insert(quote: Quote){
        return dao.insert(quote)
    }

    override suspend fun delete(quote: Quote){
        return dao.delete(quote)
    }

    override fun getQuotes() : Flow<List<Quote>>{
         return dao.getQuotes()
    }

    override suspend fun getQuoteByDate(date: LocalDate) : Quote? {
        return dao.getQuoteByDate(date.toString())
    }

    override suspend fun deletePreviousQuote(date: LocalDate) {
        return dao.deletePreviousQuote(date.toString())
    }
}