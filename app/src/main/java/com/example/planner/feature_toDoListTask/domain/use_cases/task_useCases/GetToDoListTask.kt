package com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases

import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import kotlinx.coroutines.flow.Flow

class GetToDoListTask(
    private val repository: ToDoListTaskRepository,
) {
    operator fun invoke(filter: Filter = Filter.NoFilter): Flow<List<ToDoListTask>> {
        return when(filter){
            is Filter.NoFilter -> {
                repository.getAllToDoListTask()
            }
            is Filter.RepeatingTasks -> {
                repository.getRepeatingTasks()
            }
            is Filter.ByListId -> {
                repository.getTaskByListId(filter.listId)
            }
            is Filter.FavoriteTasks -> {
                repository.getFavoriteTasks()
            }
        }
    }

    sealed class Filter {
        object NoFilter: Filter()
        object RepeatingTasks: Filter()
        object FavoriteTasks: Filter()
        data class ByListId(val listId: Int) : Filter()
    }
}