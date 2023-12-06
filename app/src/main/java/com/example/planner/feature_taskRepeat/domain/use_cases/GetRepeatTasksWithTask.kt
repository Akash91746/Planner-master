package com.example.planner.feature_taskRepeat.domain.use_cases

import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetRepeatTasksWithTask(
    private val repository: RepeatTaskRepository,
) {

    operator fun invoke(
        date: LocalDate,
    ): Flow<List<RepeatTaskWithTask>> {

        return repository.getRepeatTasksWithTask(date)
    }
}