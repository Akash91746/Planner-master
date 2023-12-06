package com.example.planner.common.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = MaterialTheme.elevation.default,
    minHeight: Dp = 54.dp,
    maxHeight: Dp = 54.dp,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colors.surface,
    paddingValues: PaddingValues = PaddingValues(),
    startIcon: @Composable (() -> Unit)? = null,
    trailing: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            modifier = modifier
                .heightIn(min = minHeight, max = maxHeight)
                .combinedClickable(onClick = onClick, onLongClick = { Timber.d("Long Click") }),
            elevation = elevation,
            onClick = onClick,
            backgroundColor = backgroundColor,
            shape = shape
        ) {
            ListItemContent(
                paddingValues,
                startIcon = startIcon,
                trailing = trailing,
                content = content
            )
        }
    } else
        Card(
            modifier = modifier
                .heightIn(min = minHeight, max = maxHeight),
            elevation = elevation,
            backgroundColor = backgroundColor,
            shape = shape
        ) {
            ListItemContent(
                paddingValues,
                startIcon = startIcon,
                trailing = trailing,
                content = content
            )
        }
}

@Composable
fun ListItemContent(
    paddingValues: PaddingValues = PaddingValues(),
    startIcon: @Composable (() -> Unit)? = null,
    trailing: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(paddingValues)
    ) {
        startIcon?.let {
            it()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
            if (trailing != null) Arrangement.SpaceBetween else Arrangement.Start
        ) {

            content(this)

            Row() {
                trailing?.let {
                    it(this)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListItem() {
    PlannerTheme {
        ListItem(onClick = {}) {
            Text(
                text = "Title",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.small),
                maxLines = 1
            )
        }
    }
}