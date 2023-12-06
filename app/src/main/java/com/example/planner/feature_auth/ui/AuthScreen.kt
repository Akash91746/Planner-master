package com.example.planner.feature_auth.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.feature_auth.domain.utils.UiEvents
import com.example.planner.feature_auth.ui.components.AuthModeContainer
import com.example.planner.feature_auth.ui.components.ForgetPassword
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    navigate: (String?) -> Unit,
) {

    val state by viewModel.state.collectAsState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val keyboard = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.uiEvents) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is UiEvents.ShowForgotPasswordDialog -> {
                    modalBottomSheetState.show()
                }
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(
                            context
                        )
                    )
                }
                is UiEvents.HideKeyboard -> {
                    keyboard?.hide()
                }
                is UiEvents.RemoveFocus -> {
                    focusManager.clearFocus()
                }
                is UiEvents.Navigate -> {
                    navigate(event.path)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            ForgetPassword(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                state = state,
                onEvent = viewModel::onEvent
            )
        },
        sheetShape = MaterialTheme.shapes.large.copy(
            topEnd = CornerSize(5),
            topStart = CornerSize(5)
        )
    ) {

        AuthScreen(
            state = state,
            onEvent = viewModel::onEvent,
            navigate = navigate,
            scaffoldState = scaffoldState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen(
    state: AuthScreenState,
    onEvent: (AuthScreenEvents) -> Unit,
    navigate: (String?) -> Unit,
    scaffoldState: BackdropScaffoldState,
) {

    BackdropScaffold(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            }
        ) {
            onEvent(AuthScreenEvents.OnClickBackdropScaffold)
        },
        appBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = { navigate(null) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                },
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        backLayerContent = {
            Spacer(modifier = Modifier.padding())
        },
        backLayerBackgroundColor = MaterialTheme.colors.background.copy(alpha = 0.3f),
        frontLayerElevation = MaterialTheme.elevation.large,
        frontLayerShape = MaterialTheme.shapes.large.copy(
            topStart = CornerSize(5),
            topEnd = CornerSize(5)
        ),
        frontLayerContent = {
            AuthModeContainer(
                state = state,
                onEvent = onEvent
            )
        },
        frontLayerBackgroundColor = MaterialTheme.colors.surface,
        frontLayerScrimColor = Color.Unspecified,
        peekHeight = 250.dp,
        gesturesEnabled = false,
        stickyFrontLayer = true,
        scaffoldState = scaffoldState
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewAuthScreen() {

    PlannerTheme {
        AuthScreen(
            state = AuthScreenState(),
            onEvent = {},
            navigate = {},
            scaffoldState = rememberBackdropScaffoldState(
                initialValue = BackdropValue.Revealed
            )
        )
    }

}