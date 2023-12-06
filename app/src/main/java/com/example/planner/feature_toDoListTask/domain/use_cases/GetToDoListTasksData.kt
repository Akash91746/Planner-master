package com.example.planner.feature_toDoListTask.domain.use_cases

import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_taskRepeat.domain.use_cases.GetRepeatTasksWithTask
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.models.ToDoListWithTasks
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoList.domain.utils.DefaultToDoLists
import com.example.planner.feature_toDoListTask.domain.utils.ToDoListTaskType
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class GetToDoListTasksData(
    private val repository: ToDoListRepository,
    private val getRepeatTasksWithTask: GetRepeatTasksWithTask
) {

    private val defaultToDoLists = DefaultToDoLists

    operator fun invoke(getData: GetData): Flow<ToDoListTaskData> {

        if (getData is GetData.Date) {
            val date = getData.date

            return getRepeatTasksWithTask(date).map {
                convert(null, it)
            }
        }

        val listId = (getData as GetData.ListId).listId

        if (listId == defaultToDoLists.TODAY_LIST.id) {
            val list = defaultToDoLists.TODAY_LIST

            val date = LocalDate.now()

            return getRepeatTasksWithTask(date).map {
                convert(list, it)
            }
        }

        if (listId == defaultToDoLists.YESTERDAY_LIST.id) {
            val list = defaultToDoLists.YESTERDAY_LIST
            val date = LocalDate.now().minusDays(1)

            return getRepeatTasksWithTask(date).map {
                convert(list, it)
            }
        }

//        if (listId == defaultToDoLists.FAVORITES_LIST.id) {
//            val list = defaultToDoLists.FAVORITES_LIST

//            return trackerListItemRepository.getFavoriteTasks().map { taskList ->
//                convert(ToDoListWithTasks(list, taskList))
//            }
//        }

        return repository.getListWithTasks(listId).map {
            convert(it)
        }
    }

    private fun convert(data: ToDoListWithTasks): ToDoListTaskData {

        return ToDoListTaskData(
            toDoList = data.toDoList,
            taskList = data.items.map { ToDoListTaskType.Normal(it) },
        )
    }

    private fun convert(list: ToDoList?, data: List<RepeatTaskWithTask>): ToDoListTaskData {
        return ToDoListTaskData(
            toDoList = list,
            taskList = data.map { repeatTaskWithTask ->
                ToDoListTaskType.RepeatTask(repeatTaskWithTask)
            }
        )
    }

    sealed class GetData {
        data class ListId(val listId: Int) : GetData()

        data class Date(val date: LocalDate) : GetData()
    }

    data class ToDoListTaskData(
        val toDoList: ToDoList?,
        val taskList: List<ToDoListTaskType>
    )
}