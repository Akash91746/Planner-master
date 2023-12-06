package com.example.planner.feature_progress.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planner.feature_progress.ui.components.ArcIcon
import com.example.planner.feature_progress.ui.components.ProgressIndicator
import com.example.planner.ui.theme.spacing
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProgressHeader(
    modifier: Modifier = Modifier,
    progressTitle: String = "Daily Progress",
    totalTask: Int,
    completedTask: Int,
    onClickHeader: (() -> Unit)? = null,
) {

    if (onClickHeader != null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            onClick = onClickHeader,
            elevation = 6.dp
        ) {
            ProgressHeaderContent(
                progressTitle = progressTitle,
                totalTask = totalTask,
                completedTask = completedTask
            )
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = 6.dp
        ) {
            ProgressHeaderContent(
                progressTitle = progressTitle,
                totalTask = totalTask,
                completedTask = completedTask
            )
        }
    }
}

@Composable
fun ProgressHeaderContent(
    progressTitle: String = "Daily Progress",
    totalTask: Int,
    completedTask: Int,
    onClickHeader: (() -> Unit)? = null,
) {
    val progress =
        remember(totalTask, completedTask) {
            if(totalTask == 0 && completedTask == 0) return@remember mutableStateOf(0.0)

            val value: Double = completedTask.toDouble() / totalTask.toDouble()

            mutableStateOf(value)
        }

    val mediumSpacing = MaterialTheme.spacing.medium
    val smallSpacing = MaterialTheme.spacing.small

    val progressText = remember(key1 = completedTask, key2 = totalTask) {
        "$completedTask/$totalTask"
    }

    Row {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressIndicator(
                modifier = Modifier
                    .padding(mediumSpacing)
                    .size(75.dp),
                percent = progress.value,
                secondaryColor = Color(0xFFD5D5D5)
            )


            Column {
                Text(text = progressTitle, style = MaterialTheme.typography.subtitle1)
                Row(
                    modifier = Modifier.padding(top = smallSpacing),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = progressText,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.primary
                    )

                    Text(
                        modifier = Modifier.padding(start = smallSpacing),
                        text = "task done",
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                }
            }
        }
        if (onClickHeader != null)
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                ArcIcon(onClick = onClickHeader)
            }
    }
}