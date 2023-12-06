package com.example.planner.feature_auth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import com.example.planner.R
import com.example.planner.feature_auth.domain.utils.AuthMode
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.ui.theme.spacing
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthModeContainer(
    modifier: Modifier = Modifier,
    state: AuthScreenState,
    authModes: Array<String> = stringArrayResource(id = R.array.auth_modes),
    onEvent: (AuthScreenEvents) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = AuthMode.SIGN_IN.ordinal)

    LaunchedEffect(key1 = state.authMode){
        pagerState.animateScrollToPage(state.authMode.ordinal)
    }

    Column(
        modifier = modifier,
    ) {

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.Transparent
        ) {
            authModes.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { onEvent(AuthScreenEvents.OnChangeAuthMode(AuthMode.values()[index])) },
                    text = {
                        Text(text = title)
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            count = 2,
            itemSpacing = MaterialTheme.spacing.medium,
            userScrollEnabled = false,
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                AuthMode.SIGN_UP.ordinal -> {
                    SignUpUser(
                        state = state,
                        onEvent = onEvent,
                        isLoading = state.isLoading
                    )
                }
                AuthMode.SIGN_IN.ordinal -> {
                    SignInUser(
                        state = state,
                        onEvent = onEvent,
                        isLoading = state.isLoading
                    )
                }
                else -> {
                    SignInUser(
                        state = state,
                        onEvent = onEvent,
                        isLoading = state.isLoading
                    )
                }
            }
        }
    }

}