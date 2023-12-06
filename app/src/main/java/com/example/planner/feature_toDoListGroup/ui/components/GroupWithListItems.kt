package com.example.planner.feature_toDoListGroup.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planner.common.ui.components.DismissibleItem
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.ui.components.ToDoListItem
import com.example.planner.feature_toDoListGroup.domain.models.GroupWithItems
import com.example.planner.ui.theme.spacing

@Composable
fun GroupWithListItems(
    data: GroupWithItems,
    onEditToDoListGroup: () -> Unit,
    onDeleteToDoListGroup: () -> Unit,
    addNewListToGroup: () -> Unit,
    onClickToDoList: (ToDoList) -> Unit,
    onEditToDoList: (ToDoList) -> Unit,
    onDeleteToDoList: (ToDoList) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        DismissibleItem(onEdit = onEditToDoListGroup, onDelete = onDeleteToDoListGroup) {

            GroupComp(
                data = data.group,
                isExpanded = isExpanded,
                onClickAddToGroup = addNewListToGroup
            ) {
                isExpanded = !isExpanded
            }
        }

        AnimatedVisibility(visible = isExpanded && data.items.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .heightIn(max = 250.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.3f))
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(MaterialTheme.spacing.small)
                ) {
                    items(data.items, key = { it.id }) { item ->
                        DismissibleItem(
                            onEdit = { onEditToDoList(item) },
                            onDelete = { onDeleteToDoList(item) }) {
                            ToDoListItem(
                                toDoList = item
                            ) {
                                onClickToDoList(item)
                            }
                        }
                    }
                }
            }
        }
    }
}