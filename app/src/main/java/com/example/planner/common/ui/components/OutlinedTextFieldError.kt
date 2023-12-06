package com.example.planner.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.ui.theme.PlannerTheme

@Composable
fun OutlinedTextFieldError(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    label: String,
    errorMessage: String? = null,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    onValueChange: (newValue: String) -> Unit,
) {

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = textFieldModifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = errorMessage !== null,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            enabled = enabled,
            visualTransformation = visualTransformation
        )

        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    label: String,
    errorMessage: String? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    onValueChange: (newValue: String) -> Unit,
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = textFieldModifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = errorMessage !== null,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            enabled = enabled,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (value.isNotEmpty()) {
                    val icon = if (passwordVisible) {
                        com.example.planner.R.drawable.baseline_visibility_24
                    } else {
                        com.example.planner.R.drawable.baseline_visibility_off_24
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "Toggle password on or off"
                        )
                    }
                }
            },
        )

        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedTextFieldError() {
    PlannerTheme {
        OutlinedTextFieldError(
            value = "Value",
            label = "Some Label",
            onValueChange = {},
            errorMessage = "Invalid"
        )
    }
}