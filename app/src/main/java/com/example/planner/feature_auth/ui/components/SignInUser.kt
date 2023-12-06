package com.example.planner.feature_auth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.R
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.common.ui.components.PasswordTextField
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun SignInUser(
    modifier: Modifier = Modifier,
    state: AuthScreenState,
    onEvent: (AuthScreenEvents) -> Unit,
    isLoading: Boolean = false,
) {

    Column(
        modifier = modifier
    ) {

        OutlinedTextFieldError(
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
            value = state.email,
            label = stringResource(R.string.email_field_label),
            onValueChange = { onEvent(AuthScreenEvents.OnChangeEmail(it)) },
            singleLine = true,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            errorMessage = state.emailErrorMessage?.asString()
        )

        PasswordTextField(
            value = state.password,
            errorMessage = state.passwordErrorMessage?.asString(),
            label = stringResource(R.string.password_field_label),
            onValueChange = { onEvent(AuthScreenEvents.OnChangePassword(it)) },
            singleLine = true,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onEvent(AuthScreenEvents.OnSubmitSignIn) })
        )

        TextButton(
            modifier = modifier.padding(bottom = MaterialTheme.spacing.large),
            onClick = { onEvent(AuthScreenEvents.OnClickForgetPassword) },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
            enabled = !isLoading
        ) {
            Text(text = stringResource(R.string.forgot_password_text))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                onClick = { onEvent(AuthScreenEvents.OnSubmitSignIn) },
                enabled = !isLoading
            ) {
                Text(text = stringResource(id = R.string.sign_in_text))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInUser() {
    PlannerTheme {
        SignInUser(state = AuthScreenState(), onEvent = {})
    }
}
