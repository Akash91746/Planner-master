package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup

sealed class DeleteItemType {
    data class List(val toDoList: ToDoList): DeleteItemType()

    data class Group(val toDoListGroup: ToDoListGroup) : DeleteItemType()
}
