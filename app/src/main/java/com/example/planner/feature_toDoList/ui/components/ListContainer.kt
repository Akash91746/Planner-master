package com.example.planner.feature_toDoList.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.common.ui.components.DividerWithTitle
import com.example.planner.common.ui.components.IconPickerItem
import com.example.planner.common.ui.components.ListItem
import com.example.planner.feature_progress.ui.components.ProgressHeader
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.utils.*
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.example.planner.feature_toDoListGroup.ui.components.GroupWithListItems
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun ListContainer(
    modifier: Modifier = Modifier,
    list: List<ListItemTypes>,
    todayListData: TaskCount,
    onEvent: (ToDoListScreenEvents) -> Unit,
) {

    fun onEditToDoList(toDoList: ToDoList) {
        onEvent(ToDoListScreenEvents.OnChangeFormMode(BottomSheetFormMode.Update.List(toDoList)))
    }

    fun onDeleteToDoList(toDoList: ToDoList) {
        onEvent(ToDoListScreenEvents.OnDelete(DeleteItemType.List(toDoList)))
    }

    fun onEditToDoListGroup(toDoListGroup: ToDoListGroup) {
        onEvent(ToDoListScreenEvents.OnChangeFormMode(BottomSheetFormMode.Update.Group(toDoListGroup)))
    }

    fun onDeleteToDoListGroup(toDoListGroup: ToDoListGroup) {
        onEvent(ToDoListScreenEvents.OnDelete(DeleteItemType.Group(toDoListGroup)))
    }

    fun onClick(toDoList: ToDoList) {
        onEvent(ToDoListScreenEvents.OnClickToDoList(toDoList))
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {

        item(key = "progress_header") {
            ProgressHeader(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                totalTask = todayListData.totalTask,
                completedTask = todayListData.completedTask
            ) {
                onEvent(ToDoListScreenEvents.OnClickProgressHeader)
            }
        }

        item(key = "today_header") {
            ListItem(
                startIcon = {
                    IconPickerItem(icon = PlannerIcons.CALENDAR)
                },
                onClick = {
                    onEvent(ToDoListScreenEvents.OnClickTodayTasks)
                },
                trailing = {
                    if (todayListData.remainingTask > 0)
                        Card(
                            elevation = MaterialTheme.elevation.extraSmall,
                            modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = if (todayListData.remainingTask >= 10) "9+" else "${todayListData.remainingTask}",
                                modifier = Modifier.padding(
                                    vertical = MaterialTheme.spacing.extraSmall,
                                    horizontal = MaterialTheme.spacing.small
                                )
                            )
                        }
                }
            ) {
                Text(
                    text = "Today",
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                )
            }
        }

        item(key = "yesterday_header") {
            ListItem(
                startIcon = {
                    IconPickerItem(icon = PlannerIcons.CALENDAR)
                },
                onClick = {
                    onEvent(ToDoListScreenEvents.OnClickYesterdayTasks)
                }
            ) {
                Text(
                    text = "Yesterday",
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                )
            }
        }

        item(key = "favorite_header") {
            ToDoListItem(toDoList = DefaultToDoLists.FAVORITES_LIST) {
                onClick(DefaultToDoLists.FAVORITES_LIST)
            }
        }

        items(list, key = { it.hashCode() }) { item ->
            when (item) {
                is ListItemTypes.List -> DismissibleItem(
                    onEdit = {
                        onEditToDoList(item.toDoList)
                    },
                    onDelete = { onDeleteToDoList(item.toDoList) }
                ) {
                    ToDoListItem(toDoList = item.toDoList) { onClick((item.toDoList)) }
                }

                is ListItemTypes.Group -> GroupWithListItems(
                    data = item.groupWithItems,
                    addNewListToGroup = {
                        onEvent(
                            ToDoListScreenEvents.OnChangeFormMode(
                                BottomSheetFormMode.Add.List(
                                    item.groupWithItems.group.id
                                )
                            )
                        )
                    },
                    onClickToDoList = { onClick(it) },
                    onEditToDoList = { onEditToDoList(it) },
                    onDeleteToDoList = { onDeleteToDoList(it) },
                    onDeleteToDoListGroup = { onDeleteToDoListGroup(item.groupWithItems.group) },
                    onEditToDoListGroup = { onEditToDoListGroup(item.groupWithItems.group) }
                )

                is ListItemTypes.Divider -> DividerWithTitle(
                    title = item.title,
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                )
            }
        }
    }
}