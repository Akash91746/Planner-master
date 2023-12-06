package com.example.planner.feature_toDoList.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.R
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.feature_toDoList.domain.utils.BottomSheetFormMode
import com.example.planner.feature_toDoList.domain.utils.ToDoListScreenEvents
import com.example.planner.feature_toDoList.domain.utils.ToDoListScreenState
import com.example.planner.ui.theme.PlannerTheme
import java.lang.RuntimeException

@Composable
fun ScaffoldContent(
    modifier: Modifier = Modifier,
    state: ToDoListScreenState,
    onEvent: (ToDoListScreenEvents) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Fab(
                onClickNewGroup = {
                    onEvent(
                        ToDoListScreenEvents.OnChangeFormMode(
                            BottomSheetFormMode.Add.Group
                        )
                    )
                }
            ) {
                onEvent(
                    ToDoListScreenEvents.OnChangeFormMode(
                        BottomSheetFormMode.Add.List()
                    )
                )
            }
        },
        topBar = {
            DefaultAppBar(
                actions = {
                    IconButton(onClick = { onEvent(ToDoListScreenEvents.OnClickTaskInsight) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_insights_24),
                            contentDescription = "See Task Insight"
                        )
                    }

                    IconButton(onClick = { onEvent(ToDoListScreenEvents.OnClickSettings) }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = stringResource(
                                R.string.settings
                            )
                        )
                    }
                }
            )
        }
    ) {

        ListContainer(
            modifier = Modifier.padding(it),
            list = state.list,
            todayListData = state.todayListData,
            onEvent = onEvent
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScaffoldContent() {
    PlannerTheme {
        ScaffoldContent(state = ToDoListScreenState(), onEvent = {})
    }
}