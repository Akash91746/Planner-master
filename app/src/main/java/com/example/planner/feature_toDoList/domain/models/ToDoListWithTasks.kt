package com.example.planner.feature_toDoList.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

data class ToDoListWithTasks(
    @Embedded
    val toDoList: ToDoList,

    @Relation(
        entity = ToDoListTask::class,
        parentColumn = "id",
        entityColumn = "listId"
    )
    val items: List<ToDoListTask>
)
