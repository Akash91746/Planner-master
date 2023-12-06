package com.example.planner.feature_toDoListTask.domain.use_cases

import android.provider.ContactsContract.Data
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.GetToDoListTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class GetToDoListTasks(
    private val getToDoListTask: GetToDoListTask,
    private val toDoListRepository: ToDoListRepository,
) {

    operator fun invoke(listId: Int) = getToDoListTask(
        GetToDoListTask.Filter.ByListId(listId)
    ).combine(toDoListRepository.getByListId(listId)) { tasks, toDoList ->

        ToDoListTaskData(
            toDoList = toDoList,
            items = formatData(tasks)
        )
    }

    fun getFavoriteTasks(): Flow<ToDoListTaskData> =
        getToDoListTask.invoke(GetToDoListTask.Filter.FavoriteTasks).map {
            return@map ToDoListTaskData(
                toDoList = null,
                items = formatData(it)
            )
        }


    private fun formatData(taskList: List<ToDoListTask>): List<DataType> {
        val list = mutableListOf<DataType>()

        val repeatTasks = mutableListOf<DataType>()
        val incompleteTasks = mutableListOf<DataType>()
        val completedTasks = mutableListOf<DataType>()

        for (task in taskList) {
            if (task.repeatMode != null) {
                repeatTasks.add(DataType.StaticTask(task))
            } else if (task.taskCompleted) {
                completedTasks.add(DataType.NormalTask(task))
            } else {
                incompleteTasks.add(DataType.NormalTask(task))
            }
        }

        if (repeatTasks.isNotEmpty()) {
            list.add(DataType.Divider("Repeating Tasks"))
            list.addAll(repeatTasks)
        }

        if (incompleteTasks.isNotEmpty()) {
            list.add(DataType.Divider("Incomplete Tasks"))
            list.addAll(incompleteTasks)
        }

        if (completedTasks.isNotEmpty()) {
            list.add(DataType.Divider("Completed Tasks"))
            list.addAll(completedTasks)
        }

        return list
    }

    data class ToDoListTaskData(
        val toDoList: ToDoList?,
        val items: List<DataType>,
    )
}

sealed class DataType(val id: Int) {
    data class Divider(val title: String) : DataType(title.hashCode())

    data class NormalTask(val toDoListTask: ToDoListTask) : DataType(toDoListTask.id)

    data class StaticTask(val toDoListTask: ToDoListTask) : DataType(toDoListTask.id)
}