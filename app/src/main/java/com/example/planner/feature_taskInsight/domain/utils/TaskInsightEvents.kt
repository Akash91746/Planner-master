package com.example.planner.feature_taskInsight.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import java.time.LocalDate

sealed class TaskInsightEvents {

    data class InitData(val taskId: Int?) : TaskInsightEvents()
    data class OnChangeFromDate(val date: LocalDate) : TaskInsightEvents()
    data class OnChangeToDate(val date: LocalDate) : TaskInsightEvents()
    data class OnDelete(val repeatTask: RepeatTask) : TaskInsightEvents()
    data class OnClickCheckBox(val repeatTask: RepeatTask): TaskInsightEvents()
    data class OnClickFavorite(val toDoListTask: ToDoListTask): TaskInsightEvents()
    data class OnChangeTaskInsightType(val type: TaskInsightType) : TaskInsightEvents()
    data class OnChangeSearchQuery(val query: String) : TaskInsightEvents()
    data class OnSelectTask(val task: ToDoListTask) : TaskInsightEvents()
    object OnClearSearchQuery : TaskInsightEvents()
    object OnClickNewEntry : TaskInsightEvents()
    data class AddNewEntry(val date: LocalDate) : TaskInsightEvents()
}