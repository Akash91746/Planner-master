package com.example.planner.feature_userProfile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UpdatePasswordComp(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onClickExpand: () -> Unit,
) {

    Card(modifier = modifier) {
        AnimatedVisibility(visible = !expanded) {
            
        }
    }

}