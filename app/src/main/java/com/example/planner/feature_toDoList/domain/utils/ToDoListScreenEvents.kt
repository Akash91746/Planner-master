package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.feature_toDoList.domain.models.ToDoList

sealed class ToDoListScreenEvents {

    data class OnClickToDoList(val toDoList: ToDoList) : ToDoListScreenEvents()
    data class OnDelete(val type: DeleteItemType): ToDoListScreenEvents()
    data class OnChangeFormMode(val formMode: BottomSheetFormMode): ToDoListScreenEvents()

    data class OnChangeTitle(val value: String) : ToDoListScreenEvents()
    data class OnChangeIcon(val value: Int) : ToDoListScreenEvents()
    object OnSubmit : ToDoListScreenEvents()
    object ResetForm: ToDoListScreenEvents()

    object OnClickProgressHeader : ToDoListScreenEvents()
    object OnClickTaskInsight: ToDoListScreenEvents()

    object OnClickTodayTasks: ToDoListScreenEvents()

    object OnClickYesterdayTasks: ToDoListScreenEvents()

    object OnClickSettings: ToDoListScreenEvents()

}
