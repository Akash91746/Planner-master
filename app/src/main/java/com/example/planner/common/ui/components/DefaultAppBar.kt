package com.example.planner.common.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.planner.R

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    actions: @Composable RowScope.() -> Unit = {},
    onClickBack: (() -> Unit)? = null,
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = if (onClickBack != null) {
            {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        } else null,
        actions = actions
    )

}