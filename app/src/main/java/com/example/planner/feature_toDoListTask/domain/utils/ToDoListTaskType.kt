package com.example.planner.feature_toDoListTask.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

sealed class ToDoListTaskType(
    val id: Int,
) {
    data class Normal(val data: ToDoListTask) : ToDoListTaskType(data.hashCode())
    data class RepeatTask(val data: RepeatTaskWithTask) : ToDoListTaskType(data.hashCode())
}

fun ToDoListTaskType.getTask(): ToDoListTask {
    return when (this) {
        is ToDoListTaskType.Normal -> {
            this.data
        }
        is ToDoListTaskType.RepeatTask -> {
            this.data.task
        }
    }
}

fun ToDoListTaskType.getRepeatTask(): RepeatTask? {
    return when (this) {
        is ToDoListTaskType.Normal -> {
            null
        }
        is ToDoListTaskType.RepeatTask -> {
            this.data.repeatTask
        }
    }
}

