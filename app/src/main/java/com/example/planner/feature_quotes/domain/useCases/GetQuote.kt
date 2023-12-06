package com.example.planner.feature_quotes.domain.useCases

import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_quotes.domain.repository.local.LocalQuotesRepository
import com.example.planner.feature_quotes.domain.repository.remote.RemoteQuotesRepository
import java.time.DayOfWeek
import java.time.LocalDate

class GetQuote(
    private val remoteQuotesRepository: RemoteQuotesRepository,
    private val localQuotesRepository: LocalQuotesRepository
) {

    suspend operator fun invoke(date: LocalDate = LocalDate.now()): Result<Quote> {

        val localResult = localQuotesRepository.getQuoteByDate(date)
        val currentDate = LocalDate.now()

        if (localResult != null){
           return Result.success(localResult)
        }

        if(date.isEqual(currentDate)){
            val remoteResult = remoteQuotesRepository.getTodayQuote()

            val remoteQuote = remoteResult.getOrNull()

            if(remoteResult.isFailure || remoteQuote == null){
                return Result.failure(Throwable("Error Fetching Quotes"))
            }

            val quote = Quote(
                date = date,
                quote = remoteQuote.q,
                author = remoteQuote.a
            )

            localQuotesRepository.insert(quote)

            return Result.success(quote)
        }

        return Result.failure(Throwable("Error Fetching Quotes"))
    }
}