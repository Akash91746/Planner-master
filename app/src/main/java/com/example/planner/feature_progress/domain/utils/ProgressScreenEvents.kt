package com.example.planner.feature_progress.domain.utils

import java.time.LocalDate

sealed class ProgressScreenEvents {

    data class OnChangeDate(val date: LocalDate): ProgressScreenEvents()

}
