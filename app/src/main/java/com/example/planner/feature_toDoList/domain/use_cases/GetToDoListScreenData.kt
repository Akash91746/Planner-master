package com.example.planner.feature_toDoList.domain.use_cases

import com.example.planner.feature_taskRepeat.domain.use_cases.RepeatTaskCount
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoList.domain.utils.ListItemTypes
import com.example.planner.feature_toDoListGroup.domain.repository.ToDoListGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class GetToDoListScreenData(
    private val toDoListRepository: ToDoListRepository,
    private val toDoListGroupRepository: ToDoListGroupRepository,
    private val repeatTaskCount: RepeatTaskCount
) {

    operator fun invoke(): Flow<ToDoListScreenData> {

        return toDoListRepository.getToDoLists(null)
            .combine(toDoListGroupRepository.getGroupWithItems()) { list, groupWithItems ->

                val listItemTypes: MutableList<ListItemTypes> = mutableListOf()

                if (list.isNotEmpty())
                    listItemTypes.add(ListItemTypes.Divider("To-Do Lists"))

                list.forEach {
                    listItemTypes.add(ListItemTypes.List(it))
                }

                if (groupWithItems.isNotEmpty())
                    listItemTypes.add(ListItemTypes.Divider("Groups"))

                groupWithItems.forEach {
                    listItemTypes.add(ListItemTypes.Group(it))
                }

                listItemTypes
            }.combine(repeatTaskCount(LocalDate.now())){ list, todayListData ->

                ToDoListScreenData(
                    list,
                    todayListData
                )
            }
    }

    data class ToDoListScreenData(
        val items: List<ListItemTypes>,
        val todayListData: TaskCount
    )
}

