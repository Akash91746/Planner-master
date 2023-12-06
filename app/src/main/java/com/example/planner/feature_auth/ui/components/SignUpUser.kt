package com.example.planner.feature_auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.R
import com.example.planner.common.ui.components.OutlinedTextFieldError
import com.example.planner.common.ui.components.PasswordTextField
import com.example.planner.feature_auth.domain.utils.AuthMode
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun SignUpUser(
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
            label = stringResource(id = R.string.email_field_label),
            onValueChange = { onEvent(AuthScreenEvents.OnChangeEmail(it)) },
            errorMessage = state.emailErrorMessage?.asString(),
            singleLine = true,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        PasswordTextField(
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
            value = state.password,
            label = stringResource(R.string.password_text),
            onValueChange = { onEvent(AuthScreenEvents.OnChangePassword(it)) },
            errorMessage = state.passwordErrorMessage?.asString(),
            singleLine = true,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        PasswordTextField(
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.large),
            value = state.confirmPassword,
            label = stringResource(R.string.confirm_password),
            onValueChange = { onEvent(AuthScreenEvents.OnChangeConfirmPassword(it)) },
            errorMessage = state.confirmPasswordErrorMessage?.asString(),
            singleLine = true,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onEvent(AuthScreenEvents.OnSubmitSignUp) })
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = { onEvent(AuthScreenEvents.OnSubmitSignUp) },
                enabled = !isLoading
            ) {
                Text(text = stringResource(R.string.sign_up_text))
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpUser() {
    PlannerTheme {
        SignUpUser(
            state = AuthScreenState(),
            onEvent = {}
        )
    }
}