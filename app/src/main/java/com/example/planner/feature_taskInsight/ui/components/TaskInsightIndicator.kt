package com.example.planner.feature_taskInsight.ui.components

import android.graphics.Typeface
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.feature_taskInsight.domain.utils.PieChartInput
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightEvents
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightState
import com.example.planner.feature_taskInsight.domain.utils.TaskInsightType
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.legend.LegendItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TaskInsightIndicator(
    modifier: Modifier = Modifier,
    state: TaskInsightState,
    onEvent: (TaskInsightEvents) -> Unit,
) {

    fun onChange(type: TaskInsightType) {
        onEvent(TaskInsightEvents.OnChangeTaskInsightType(type))
    }

    Card(
        modifier = modifier,
        elevation = MaterialTheme.elevation.small
    ) {
        Column {
            if (state.lineChartData != null)
                TabRow(
                    selectedTabIndex = state.currentTab.ordinal,
                    backgroundColor = Color.Transparent
                ) {
                    Tab(
                        selected = state.currentTab == TaskInsightType.PIE_CHART,
                        onClick = {
                            onChange(TaskInsightType.PIE_CHART)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                            text = "Pie Chart"
                        )
                    }
                    Tab(
                        selected = state.currentTab == TaskInsightType.LINE_GRAPH,
                        onClick = {
                            onChange(TaskInsightType.LINE_GRAPH)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                            text = "Line Graph"
                        )
                    }
                }

            TabContent(
                modifier = Modifier.height(250.dp),
                currentTab = state.currentTab,
                pieChartInput = state.pieCharInputList,
                chartEntryModel = state.lineChartData?.chartEntryModel,
                horizontalAxisValueFormatter = state.lineChartData?.horizontalAxisValueFormatter,
                legendTitle = remember(state.task?.tracker) {
                    listOfNotNull(
                        state.task?.tracker?.titleOne,
                        state.task?.tracker?.titleTwo
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(
    modifier: Modifier = Modifier,
    currentTab: TaskInsightType,
    pieChartInput: List<PieChartInput>?,
    chartEntryModel: ChartEntryModel?,
    legendTitle: List<String> = emptyList(),
    horizontalAxisValueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom>?,
) {

    val pagerState = rememberPagerState(
        initialPage = currentTab.ordinal,
        initialPageOffsetFraction = 0f,
    ) {
        if (chartEntryModel != null) 2 else 1
    }

    LaunchedEffect(key1 = currentTab) {
        pagerState.animateScrollToPage(page = currentTab.ordinal)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        when (page) {
            TaskInsightType.PIE_CHART.ordinal ->
                if (pieChartInput != null) TaskProgressPieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium),
                    data = pieChartInput,
                    centerTitleCircleRadius = 100f,
                    innerRadius = 130f,
                ) {
                    Text(
                        text = "Task",
                        style = MaterialTheme.typography.subtitle1
                    )
                }

            TaskInsightType.LINE_GRAPH.ordinal ->
                if (chartEntryModel != null)
                    ProvideChartStyle(
                        chartStyle = currentChartStyle.copy(
                            lineChart = currentChartStyle.lineChart.copy(
                                lines = listOf(
                                    lineSpec(lineColor = MaterialTheme.colors.secondary),
                                    lineSpec(lineColor = MaterialTheme.colors.primary)
                                )
                            )
                        )
                    ) {
                        Chart(
                            chart = lineChart(),
                            model = chartEntryModel,
                            bottomAxis = rememberBottomAxis(
                                valueFormatter = horizontalAxisValueFormatter
                                    ?: DecimalFormatAxisValueFormatter(),
                            ),
                            startAxis = rememberStartAxis(),
                            legend = if (legendTitle.isNotEmpty()) verticalLegend(
                                items = legendTitle.mapIndexed { index, legend ->
                                    LegendItem(
                                        icon = shapeComponent(
                                            Shapes.pillShape,
                                            color = if (index % 2 == 0) MaterialTheme.colors.secondary else
                                                MaterialTheme.colors.primary
                                        ),
                                        label = textComponent {
                                            padding = MutableDimensions(
                                                horizontalDp = 0f,
                                                verticalDp = 4f
                                            )
                                            typeface = Typeface.MONOSPACE
                                        },
                                        labelText = legend
                                    )
                                },
                                iconSize = 8.dp,
                                iconPadding = 10.dp
                            ) else null
                        )
                    }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChart() {
//    val data = listOf(
//        "2022-11-05" to 2f,
//        "2022-07-02" to 6f,
//        "2022-08-04" to 4f
//    ).associate { (dateString, yValue) ->
//        LocalDate.parse(dateString) to yValue
//    }

    fun getDateString(index: Int): String {
        val num = index + 1
        return if (num >= 10) "2022-10-$num" else "2022-10-0$num"
    }

    val data = List(20) { getDateString(it) to 2f * it }.associate { (dateString, yValue) ->
        LocalDate.parse(dateString) to yValue
    }

//    val data2 = listOf(
//        "2022-07-05" to 10f,
//        "2022-07-02" to 15f,
//        "2022-08-04" to 12f
//    ).associate { (dateString, yValue) ->
//        LocalDate.parse(dateString) to yValue
//    }

    val data2 = List(20) { getDateString(it) to 3f * it }.associate { (dateString, yValue) ->
        LocalDate.parse(dateString) to yValue
    }

    val xValuesToDates = data.keys.mapIndexed { index, localDate ->
        index.toFloat() to localDate
    }.toMap()

    val x2ValuesToDates = data2.keys.mapIndexed { index, localDate ->
        index.toFloat() to localDate
    }.toMap()

    //zip creates pair of values :
    // Eg, list1 - 1, 2, 4 list2 - a, b, c
    // list1 zip list 2 = (1,a) (2,b) (3,b)

    val chartEntryModel = entryModelOf(
        xValuesToDates.keys.zip(data.values, ::entryOf),
        x2ValuesToDates.keys.zip(data2.values, ::entryOf)
    )

    val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")
    val horizontalAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
            (xValuesToDates[value]
                ?: LocalDate.ofEpochDay(value.toLong())).format(dateTimeFormatter)
        }


    PlannerTheme {
        ProvideChartStyle(
            chartStyle = currentChartStyle.copy(
                lineChart = currentChartStyle.lineChart.copy(
                    lines = listOf(
                        lineSpec(lineColor = MaterialTheme.colors.secondary),
                        lineSpec(lineColor = MaterialTheme.colors.primary)
                    )
                )
            )
        ) {
            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                bottomAxis = rememberBottomAxis(
                    valueFormatter = horizontalAxisValueFormatter
                ),
                startAxis = rememberStartAxis(),
            )
        }
    }
}