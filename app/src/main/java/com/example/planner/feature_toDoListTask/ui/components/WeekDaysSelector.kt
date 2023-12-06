package com.example.planner.feature_toDoListTask.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.feature_toDoListTask.domain.utils.WEEK_DAY_LIST
import com.example.planner.feature_toDoListTask.domain.utils.WeekDay
import com.example.planner.ui.theme.PlannerTheme

@Composable
fun WeekDaysSelector(
    modifier: Modifier = Modifier,
    weekDayList: List<WeekDay>,
    errorMessage: String? = null,
    onClick: (WeekDay) -> Unit
) {

    Column(modifier = modifier) {

        LazyVerticalGrid(columns = GridCells.Adaptive(78.dp)) {
            items(weekDayList) { item ->

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.selected,
                        onCheckedChange = { onClick(item) }
                    )
                    Text(text = item.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }

        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeekDaysSelector() {
    PlannerTheme {
        WeekDaysSelector(
            weekDayList = WEEK_DAY_LIST) {

        }
    }
}