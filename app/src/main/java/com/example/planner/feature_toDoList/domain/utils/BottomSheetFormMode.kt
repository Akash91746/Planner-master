package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup

sealed class BottomSheetFormMode{

    sealed class Add : BottomSheetFormMode() {

        data class List(val groupId: Int? = null): Add()

        object Group: Add()
    }

    sealed class Update : BottomSheetFormMode() {
        data class List(val toDoList: ToDoList) : Update()

        data class Group(val toDoListGroup: ToDoListGroup): Update()
    }

    object Hidden: BottomSheetFormMode()
}
