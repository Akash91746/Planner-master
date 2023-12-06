package com.example.planner.feature_progress.domain.utils

import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import com.example.planner.feature_toDoListTask.domain.utils.BaseTaskState
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskType
import java.time.LocalDate

data class ProgressScreenState(
    val progressDate: LocalDate = LocalDate.now(),
    val taskCount: TaskCount = TaskCount(0,0),
)
