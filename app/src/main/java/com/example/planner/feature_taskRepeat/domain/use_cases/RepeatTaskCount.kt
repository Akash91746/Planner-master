package com.example.planner.feature_taskRepeat.domain.use_cases

import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RepeatTaskCount(
    private val repeatTaskRepository: RepeatTaskRepository
) {

    operator fun invoke(date: LocalDate? = null): Flow<TaskCount> {
        return repeatTaskRepository.getRepeatTasks(date).map { list ->
            val totalTask = list.size

            val completedTask = list.count { it.taskDone }

            TaskCount(
                totalTask, completedTask
            )
        }
    }
}