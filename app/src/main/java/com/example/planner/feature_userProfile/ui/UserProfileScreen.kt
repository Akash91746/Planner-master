package com.example.planner.feature_userProfile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.example.planner.R
import com.example.planner.common.ui.components.DefaultAppBar
import com.example.planner.common.utils.AppScreens
import com.example.planner.feature_userProfile.ui.components.ChangePassword
import com.example.planner.feature_userProfile.ui.components.UserProfile
import com.example.planner.feature_userProfile.utils.UiEvents
import com.example.planner.feature_userProfile.utils.UserProfileEvents
import com.example.planner.feature_userProfile.utils.UserProfileState
import com.example.planner.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserProfileScreen(
    state: UserProfileState,
    onEvent: (UserProfileEvents) -> Unit,
    uiEvents: Flow<UiEvents>,
    navigate: (String?) -> Unit,
) {

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiEvents) {
        uiEvents.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }
                is UiEvents.RemoveFocus -> {
                    focusManager.clearFocus()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = remember{ MutableInteractionSource() },
            indication = null,
            onClick = { onEvent(UserProfileEvents.OnClickScaffold) }
        ),
        topBar = {
            DefaultAppBar (
                title = stringResource(id = R.string.account_text)
            ){
                navigate(null)
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->

        if (state.firebaseUser == null)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    navigate(AppScreens.AuthScreen.navPath)
                }) {
                    Text(text = stringResource(id = com.example.planner.R.string.sign_in_text))
                }
            }
        else
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
            ) {
                UserProfile(
                    email = state.firebaseUser.email,
                    emailVerified = state.firebaseUser.isEmailVerified,
                    isLoading = state.isLoading
                ) {
                    onEvent(UserProfileEvents.OnClickVerifyEmail)
                }

                ChangePassword(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                    state = state,
                    onEvent = onEvent
                )

            }
    }

}