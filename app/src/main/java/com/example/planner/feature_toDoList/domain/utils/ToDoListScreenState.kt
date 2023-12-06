package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.feature_taskRepeat.domain.utils.TaskCount

data class ToDoListScreenState(
    val formState: FormState = FormState(),
    val list: List<ListItemTypes> = emptyList(),
    val todayListData: TaskCount = TaskCount(0,0)
)
