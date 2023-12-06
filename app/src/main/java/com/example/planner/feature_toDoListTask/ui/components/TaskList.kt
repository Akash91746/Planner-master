package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.planner.R
import com.example.planner.common.ui.components.DividerWithTitle
import com.example.planner.feature_taskRepeat.ui.components.QuoteCard
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.DataType
import com.example.planner.feature_toDoListTask.domain.utils.*
import com.example.planner.ui.theme.spacing
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    state: ToDoListTaskState,
    onEvent: (ToDoListTaskEvents) -> Unit,
) {

    fun handleDeleteTask(task: ToDoListTask) {
        onEvent(ToDoListTaskEvents.OnDeleteTask(task))
    }

    fun handleEditTask(task: ToDoListTask) {
        onEvent(ToDoListTaskEvents.OnEditTask(task))
    }

    fun handleOnClickFavorite(task: ToDoListTask) {
        onEvent(ToDoListTaskEvents.ToggleFavoriteStatus(task))
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {

        if (state.quote != null)
            item(key = state.quote.hashCode()) {
                QuoteCard(
                    data = state.quote,
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.medium)
                        .fillMaxWidth()
                )
            }

        items(state.listTasks, key = { it.hashCode() }) { listItem ->

            if (listItem is DataType.Divider) {
                DividerWithTitle(
                    title = listItem.title,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                )
            }

            if (listItem is DataType.NormalTask) {
                DismissibleListTask(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .animateItemPlacement(),
                    listTask = listItem.toDoListTask,
                    onEdit = {
                        handleEditTask(listItem.toDoListTask)
                    },
                    onClickFavorite = {
                        handleOnClickFavorite(listItem.toDoListTask)
                    },
                    onClickCheckBox = {
                        onEvent(ToDoListTaskEvents.ToggleTaskDone(listItem.toDoListTask))
                    }
                ) {
                    handleDeleteTask(listItem.toDoListTask)
                }
            }

            if (listItem is DataType.StaticTask) {
                DismissibleListTask(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .animateItemPlacement(),
                    listTask = listItem.toDoListTask,
                    onEdit = {
                        handleEditTask(listItem.toDoListTask)
                    },
                    textDecorationEnabled = false,
                    onClickFavorite = {
                        handleOnClickFavorite(listItem.toDoListTask)
                    },
                    extraTrailingIcons = {
                        IconButton(onClick = {
                            onEvent(ToDoListTaskEvents.OnClickTaskInsight(listItem.toDoListTask.id))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_insights_24),
                                contentDescription = stringResource(R.string.task_insight_content_desc)
                            )
                        }
                    }
                ) {
                    handleDeleteTask(listItem.toDoListTask)
                }
            }
        }
    }
}