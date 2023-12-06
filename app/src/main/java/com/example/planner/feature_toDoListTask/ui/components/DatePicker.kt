package com.example.planner.feature_toDoListTask.ui.components

import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.common.utils.PlannerDatePicker
import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing
import java.time.LocalDate

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    repeatMode: RepeatMode? = null,
    errorMessage: String? = null,
    maxDate: PlannerDatePicker.DatePickerRange.MaxDate? = null,
    title: String = "Set Date",
    onChange: (LocalDate) -> Unit,
) {
    val context = LocalContext.current

    fun changeDate(year: Int, month: Int, day: Int) {
        onChange(LocalDate.of(year, month + 1, day))
    }

    fun showDialog() {

        val year = localDate.year
        val month = localDate.month.value - 1
        val day = localDate.dayOfMonth

        val datePickerDialog =
            PlannerDatePicker(
                context,
                year, month, day
            ) { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                changeDate(mYear, mMonth, mDay)
            }.also {
                if (
                    repeatMode != null && maxDate == null &&
                    repeatMode == RepeatMode.MONTHLY || repeatMode == RepeatMode.YEARLY
                ) {
                    val range = if (repeatMode == RepeatMode.MONTHLY) {
                        PlannerDatePicker.DatePickerRange.MONTH
                    } else {
                        PlannerDatePicker.DatePickerRange.YEAR
                    }
                    it.setDateRange(range)
                }

                if (maxDate != null) {
                    it.setDateRange(maxDate)
                }
            }

        datePickerDialog.show()
    }

    Column(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedButton(
                onClick = { showDialog() }
            ) {
                Text(text = title)
            }

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

            Text(
                text = localDate.toString(),
                style = MaterialTheme.typography.body2
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

@Preview(showBackground = true)
@Composable
fun PreviewDatePicker() {
    PlannerTheme {
        var date by remember {
            mutableStateOf(LocalDate.now())
        }
        DatePicker(localDate = date, onChange = {
            date = it
        }, repeatMode = RepeatMode.MONTHLY)
    }
}