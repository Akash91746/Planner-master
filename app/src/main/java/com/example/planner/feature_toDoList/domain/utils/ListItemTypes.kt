package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListGroup.domain.models.GroupWithItems

sealed class ListItemTypes {
    data class List(val toDoList: ToDoList) :
        ListItemTypes()
    data class Group(val groupWithItems: GroupWithItems) : ListItemTypes()
    data class Divider(val title: String) : ListItemTypes()
}