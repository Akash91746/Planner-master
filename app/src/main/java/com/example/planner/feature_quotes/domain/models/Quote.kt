package com.example.planner.feature_quotes.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("quote")
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val date: LocalDate = LocalDate.now(),
    val quote: String,
    val author: String
)
