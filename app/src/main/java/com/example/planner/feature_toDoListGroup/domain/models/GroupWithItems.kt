package com.example.planner.feature_toDoListGroup.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.planner.feature_toDoList.domain.models.ToDoList

data class GroupWithItems(

    @Embedded
    val group: ToDoListGroup,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val items: List<ToDoList>
)
