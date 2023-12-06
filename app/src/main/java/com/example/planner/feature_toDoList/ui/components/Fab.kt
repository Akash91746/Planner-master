package com.example.planner.feature_toDoList.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.cornerSize
import com.example.planner.ui.theme.spacing

@Composable
fun Fab(
    modifier: Modifier = Modifier,
    onClickNewGroup: () -> Unit,
    onCLickNewList: () -> Unit
) {

    var fabExpanded by remember {
        mutableStateOf(false)
    }

    val transition = updateTransition(targetState = fabExpanded, label = "Fab Transition")

    val rotation: Float by transition.animateFloat(label = "Fab rotation") {
        if (it) 45f else 0f
    }

    fun handleOnClick() {
        fabExpanded = !fabExpanded
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {

        AnimatedVisibility(visible = fabExpanded) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FabButton(
                    text = "Add List",
                ) {
                    onCLickNewList()
                    handleOnClick()
                }
                FabButton(
                    text = "Add Group",
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                ) {
                    onClickNewGroup()
                    handleOnClick()
                }
            }
        }

        FloatingActionButton(
            onClick = { handleOnClick() },
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add new list or group",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
fun FabButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        shape = MaterialTheme.shapes.medium.copy(all = MaterialTheme.cornerSize.extraLarge),
        modifier = modifier
    ) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}

@Preview
@Composable
fun PreviewFabButton() {
    PlannerTheme {
        FabButton(text = "List") {

        }
    }
}

@Preview
@Composable
fun PreviewFab() {
    PlannerTheme {
        Fab(onClickNewGroup = {}) {}
    }
}