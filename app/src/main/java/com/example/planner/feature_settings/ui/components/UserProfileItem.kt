package com.example.planner.feature_settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.R
import com.example.planner.ui.theme.PlannerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfileItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    ListItem(
        modifier = modifier.clickable { onClick() },
        icon = {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = stringResource(id = R.string.profile_icon_desc)
            )
        },
        trailing = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.profile_expand_icon_desc)
                )
            }
        }
    ) {
        Text(text = stringResource(R.string.account_text))
    }

}

@Preview
@Composable
fun PreviewUserProfileItem() {
    PlannerTheme {
        UserProfileItem() {

        }
    }
}