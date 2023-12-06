package com.example.planner.feature_userProfile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.R
import com.example.planner.common.ui.components.PasswordTextField
import com.example.planner.feature_userProfile.utils.UserProfileEvents
import com.example.planner.feature_userProfile.utils.UserProfileState
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun ChangePassword(
    modifier: Modifier = Modifier,
    state: UserProfileState,
    onEvent: (UserProfileEvents) -> Unit,
) {

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedButton(
                onClick = { onEvent(UserProfileEvents.ToggleUpdatePassword) }
            ) {
                Text(
                    text = stringResource(R.string.change_password_label),
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                )
                Icon(
                    imageVector = if (state.changePasswordExpanded)
                        Icons.Rounded.KeyboardArrowUp else
                        Icons.Rounded.KeyboardArrowDown,
                    contentDescription = ""
                )
            }

            AnimatedVisibility(visible = state.changePasswordExpanded) {
                Column {
                    PasswordTextField(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                        value = state.currentPassword,
                        label = stringResource(R.string.current_password_text_field_label),
                        onValueChange = { onEvent(UserProfileEvents.OnChangeCurrentPassword(it)) },
                        errorMessage = state.currentPasswordError?.asString(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    PasswordTextField(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                        value = state.newPassword,
                        label = stringResource(R.string.new_password_text_field_label),
                        onValueChange = { onEvent(UserProfileEvents.OnChangeNewPassword(it)) },
                        errorMessage = state.newPasswordError?.asString(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    PasswordTextField(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                        value = state.newConfirmPassword,
                        label = stringResource(R.string.confirm_new_password_text_field_label),
                        onValueChange = { onEvent(UserProfileEvents.OnChangeConfirmNewPassword(it)) },
                        errorMessage = state.newConfirmPasswordError?.asString(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { onEvent(UserProfileEvents.OnSubmitChangePassword) })
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {onEvent(UserProfileEvents.OnClickResetCurrentPassword)},
                            enabled = !state.isLoading,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                        ) {
                            Text(text = stringResource(id = R.string.forgot_password_text))
                        }
                        
                        Button(
                            onClick = { onEvent(UserProfileEvents.OnSubmitChangePassword) },
                            enabled = !state.isLoading
                        ) {
                            Text(text = stringResource(R.string.done_text))
                        }
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewChangePassword() {
    PlannerTheme {
        ChangePassword(state = UserProfileState(), onEvent = {})
    }
}