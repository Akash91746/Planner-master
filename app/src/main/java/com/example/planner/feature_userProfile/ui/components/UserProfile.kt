package com.example.planner.feature_userProfile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.R
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    email: String?,
    emailVerified: Boolean = false,
    isLoading: Boolean = false,
    onClickVerifyEmail: () -> Unit,
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = stringResource(id = R.string.profile_icon_desc)
            )

            Spacer(modifier = Modifier.padding(bottom = MaterialTheme.spacing.small))

            if (email != null) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.small)
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (emailVerified) Color.Green else MaterialTheme.colors.error
                                ),
                                shape = MaterialTheme.shapes.small.copy(all = CornerSize(50))
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(all = 2.dp)
                                .size(16.dp),
                            imageVector = if (emailVerified) Icons.Rounded.Done else
                                Icons.Rounded.Close,
                            contentDescription = if (emailVerified)
                                stringResource(id = R.string.email_verified_desc) else
                                stringResource(R.string.email_not_verified_icon_desc),
                            tint = if (emailVerified) Color.Green else MaterialTheme.colors.error
                        )
                    }

                    Text(text = email)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (emailVerified){
                        Text(
                            text = stringResource(R.string.email_verified_text) ,
                            color =  Color.Green,
                            style = MaterialTheme.typography.caption
                        )
                    }else {
                        OutlinedButton(
                            onClick = onClickVerifyEmail,
                            enabled = !isLoading
                        ) {
                            Text(text = "Verify Email")
                        }
                    }
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    PlannerTheme {
        UserProfile(
            email = "test@gmail.com",
            modifier = Modifier.fillMaxWidth(),
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewEmailVerifiedUserProfile() {
    PlannerTheme {
        UserProfile(
            email = "test@gmail.com",
            modifier = Modifier.fillMaxWidth(),
            emailVerified = true
        ) {

        }
    }
}