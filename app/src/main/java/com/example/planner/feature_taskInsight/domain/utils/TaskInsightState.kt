package com.example.planner.feature_taskInsight.domain.utils

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import java.time.LocalDate

data class TaskInsightState(
    val fromDate: LocalDate = LocalDate.now().minusDays(7),
    val toDate: LocalDate = LocalDate.now(),
    val task: ToDoListTask? = null,
    val list: List<RepeatTask> = emptyList(),
    val pieCharInputList: List<PieChartInput>? = null,
    val lineChartData: LineChartData? = null,
    val currentTab: TaskInsightType = TaskInsightType.PIE_CHART,
    val autoCompleteState: AutoCompleteState<ValueAutoCompleteEntity<ToDoListTask>> =
        AutoCompleteState(
            startItems = emptyList()
        ),
    val searchQuery: String = "",
)

data class LineChartData(
    val chartEntryModel: ChartEntryModel,
    val horizontalAxisValueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> =
        DecimalFormatAxisValueFormatter(),
)