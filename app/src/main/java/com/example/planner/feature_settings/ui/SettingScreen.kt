package com.example.planner.feature_settings.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planner.R
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.common.utils.AppScreens
import com.example.planner.feature_settings.ui.components.UserProfileItem
import com.example.planner.feature_settings.utils.SettingScreenEvents
import com.example.planner.feature_settings.utils.SettingScreenState
import com.example.planner.feature_settings.utils.UiEvents
import com.example.planner.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingScreen(
    state: SettingScreenState,
    onEvent: (SettingScreenEvents) -> Unit,
    uiEvents: Flow<UiEvents>,
    navigate: (String?) -> Unit,
) {

    LaunchedEffect(key1 = uiEvents){
        uiEvents.collectLatest {event ->
            when(event){
                is UiEvents.Navigate -> {
                    navigate(event.path)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.settings)
            ) {
                navigate(null)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(all = MaterialTheme.spacing.medium),
        ) {
            Card {
                if (state.firebaseUser != null) {
                    UserProfileItem {
                        onEvent(SettingScreenEvents.OnClickAccount)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.small),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { navigate(AppScreens.AuthScreen.navPath) }
                        ) {
                            Text(text = stringResource(id = R.string.sign_in_text))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            if (state.firebaseUser != null)
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.small),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { onEvent(SettingScreenEvents.OnClickSignOut) }) {
                            Text(text = stringResource(R.string.sign_out_text))
                        }
                    }
                }
        }
    }
}