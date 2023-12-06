package com.example.planner.feature_toDoListTask.domain.utils

import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.DataType
import com.example.planner.feature_toDoListTask.domain.use_cases.GetToDoListTasks

data class ToDoListTaskState(
    val toDoList: ToDoList? = null,
    val listTasks: List<DataType> = emptyList(),
    val selectedTask: ToDoListTask? = null,
    val formState: ToDoListTaskFormState = ToDoListTaskFormState(),
    val baseTaskState: BaseTaskState = BaseTaskState(),
    val quote: Quote? = null,
    val appBarTitle: UiText = UiText.StringResource(R.string.app_name)
)
