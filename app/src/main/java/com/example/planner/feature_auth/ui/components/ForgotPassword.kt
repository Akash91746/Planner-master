package com.example.planner.feature_auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.R
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun ForgetPassword(
    modifier: Modifier = Modifier,
    state: AuthScreenState,
    onEvent: (AuthScreenEvents) -> Unit,
) {

    Column(
        modifier = modifier,
    ) {

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.forgot_password_text),
            style = MaterialTheme.typography.h6
        )

        Text(
            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
            text = stringResource(R.string.forgot_password_modal_desc),
            style = MaterialTheme.typography.caption
        )

        OutlinedTextFieldError(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            value = state.forgotPasswordEmail,
            label = stringResource(id = R.string.email_field_label),
            onValueChange = { onEvent(AuthScreenEvents.OnChangeForgotPasswordEmail(it)) },
            errorMessage = state.forgotPasswordEmailErrorMessage?.asString(),
            enabled = !state.isLoading
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onEvent(AuthScreenEvents.OnSubmitForgotPassword) },
                enabled = !state.isLoading
            ) {
                Text(text = stringResource(R.string.reset_text))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewForgetPassword() {
    PlannerTheme {
        ForgetPassword(
            state = AuthScreenState(),
            onEvent = {}
        )
    }
}