package com.example.planner.feature_taskRepeat.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

sealed class RepeatTaskEvents{

    data class InitData(val date: String?) : RepeatTaskEvents()

    data class OnClickFavorite(val toDoListTask: ToDoListTask): RepeatTaskEvents()

    data class OnClickCheckBox(val repeatTask: RepeatTask,val task: ToDoListTask): RepeatTaskEvents()

    data class OnDeleteRepeatTask(val repeatTask: RepeatTask): RepeatTaskEvents()

    data class OnTrackerTaskEvent(val event: TrackerTaskEvents): RepeatTaskEvents()
}
