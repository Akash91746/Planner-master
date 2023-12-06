package com.example.planner.feature_quotes.data.data_source.local

import androidx.room.*
import com.example.planner.feature_quotes.domain.models.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("Select * from quote")
    fun getQuotes(): Flow<List<Quote>>

    @Query("Select * from quote where date == (:date)")
    suspend fun getQuoteByDate(date: String) : Quote?

    @Query("DELETE FROM quote where date < (:date)")
    suspend fun deletePreviousQuote(date: String)
}