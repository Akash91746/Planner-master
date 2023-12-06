package com.example.planner.feature_toDoListTask.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.ui.theme.PlannerTheme
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*


@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime? = null,
    simpleDateFormat: SimpleDateFormat = SimpleDateFormat("hh:mm a z", Locale.getDefault()),
    errorMessage: String? = null,
    onChange: (LocalTime) -> Unit
) {

    val context = LocalContext.current

    fun changeTime(hour: Int, minute: Int) {
        onChange(LocalTime.of(hour,minute))
    }

    fun showTimeDialog() {
        var localTime = LocalTime.now()

        if (time != null) {
            localTime = time
        }

        val hour = localTime.hour
        val minute = localTime.minute

        val timePickerDialog = TimePickerDialog(
            context,
            { _, mHour: Int, mMinute: Int ->
                changeTime(mHour, mMinute)
            }, hour, minute, false
        )

        timePickerDialog.show()
    }

    Column(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedButton(onClick = { showTimeDialog() }) {
                Text(text = "Set Reminder")
            }

            Text(
                text = if (time != null) simpleDateFormat.format(time) else "",
                style = MaterialTheme.typography.body2,
            )
        }

        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }

    }
}

@Preview
@Composable
fun PreviewTimePicker() {
    PlannerTheme {
        TimePicker {}
    }
}