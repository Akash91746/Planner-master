package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.ui.theme.PlannerTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun TaskInsightChart() {
    val chartModel = entryModelOf(1,2,3,10)
    Chart(
        chart = lineChart(),
        model = chartModel,
        startAxis = startAxis(),
        bottomAxis = bottomAxis()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskInsightChart() {
    PlannerTheme {
        TaskInsightChart()
    }
}