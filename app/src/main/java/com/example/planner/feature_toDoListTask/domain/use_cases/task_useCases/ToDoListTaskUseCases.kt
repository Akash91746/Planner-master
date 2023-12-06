package com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases

data class ToDoListTaskUseCases(
    val deleteToDoListTask: DeleteToDoListTask,
    val insertToDoListTask: InsertToDoListTask,
    val updateToDoListTask: UpdateToDoListTask,
    val getTasks: GetToDoListTask
)