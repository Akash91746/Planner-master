package com.example.planner.feature_taskInsight.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.planner.feature_taskInsight.domain.utils.LineGraphInput
import com.example.planner.feature_taskInsight.domain.utils.LineGraphInputData
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@OptIn(ExperimentalTextApi::class)
@Composable
fun LineGraphV2(
    modifier: Modifier = Modifier,
    input: List<LineGraphInput>,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    labelPadding: Dp = MaterialTheme.spacing.small,
    axisColor: Color = Color.LightGray,
    axisStrokeWidth : Float = 8f,
    pointRadius: Float = 8f,
    lineStrokeWidth: Float = 4f
) {

    val textMeasurer = rememberTextMeasurer()

    val spacing = MaterialTheme.spacing
    val themeColors = MaterialTheme.colors

    Canvas(modifier = modifier) {

        val xPoints = input.map {
            it.x
        }.distinctBy { it.value }.sortedBy { it.value }

        val yPoints = input.map {
            it.y
        }.distinctBy { it.value }.sortedBy { it.value }

        val xAxisLabelTextMeasure = textMeasurer.measure(
            AnnotatedString(
                xPoints[0].title ?: xPoints[0].value.toString(),
                spanStyle = textStyle.toSpanStyle()
            ), textStyle
        )

        val longestYAxisLabelText = yPoints.maxOf { (it.title ?: it.value.toString()).length }

        val yAxisLabelTextMeasure = textMeasurer.measure(
            AnnotatedString(
                "A".repeat(longestYAxisLabelText),
                spanStyle = textStyle.toSpanStyle()
            ),
            textStyle
        )

        val originOffset = Offset(0f, size.height)

        val chartOrigin = Offset(
            originOffset.x + yAxisLabelTextMeasure.size.width + spacing.extraSmall.toPx(),
            originOffset.y - xAxisLabelTextMeasure.size.height - spacing.extraSmall.toPx()
        )

        //draw X axis Label Text

        val xCoordinates = mutableMapOf<LineGraphInputData, Float>()

        var xAxisLabelOffset = Offset(
            chartOrigin.x + labelPadding.toPx(),
            originOffset.y - xAxisLabelTextMeasure.size.height
        )

        for (i in xPoints) {

            val text = i.title ?: i.value.toString()

            val textMeasure = textMeasurer.measure(
                AnnotatedString(text), textStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = text,
                topLeft = xAxisLabelOffset,
                style = textStyle
            )

            val labelCenterCoordinates = xAxisLabelOffset.x + (textMeasure.size.width / 2)

            xCoordinates[i] = labelCenterCoordinates

            xAxisLabelOffset = xAxisLabelOffset.copy(
                x = xAxisLabelOffset.x + textMeasure.size.width + labelPadding.toPx()
            )
        }

        //draw Y Axis Label Text

        val yCoordinates = mutableMapOf<LineGraphInputData, Float>()

        val firstText = yPoints[0].title ?: yPoints[0].value.toString()

        val firstTextMeasure = textMeasurer.measure(
            AnnotatedString(firstText), textStyle
        )


        var yAxisLabelOffset = Offset(
            originOffset.x,
            chartOrigin.y - firstTextMeasure.size.height - labelPadding.toPx(),
        )

        for (i in yPoints){
            val text = i.title ?: i.value.toString()

            val textMeasure = textMeasurer.measure(
                AnnotatedString(text), textStyle
            )

            drawText(
                textMeasurer = textMeasurer,
                text = text,
                topLeft = yAxisLabelOffset,
                style = textStyle
            )

            val labelCenterCoordinates = yAxisLabelOffset.y + (textMeasure.size.height /2)

            yCoordinates[i] = labelCenterCoordinates

            yAxisLabelOffset = yAxisLabelOffset.copy(
                y = yAxisLabelOffset.y - textMeasure.size.height - labelPadding.toPx()
            )
        }

        //draw Lines
        val cap = StrokeCap.Round
        // draw X Axis Line

        val xEndOffset = Offset(
            x = size.width,
            y = chartOrigin.y
        )

        drawLine(
            color = axisColor,
            start = chartOrigin,
            end = xEndOffset,
            cap =  cap,
            strokeWidth = axisStrokeWidth
        )

        //draw Y Axis Line

        val yEndOffset = Offset(
            x = chartOrigin.x,
            y = 0f
        )

        drawLine(
            color = axisColor,
            start = chartOrigin,
            end =  yEndOffset,
            cap = cap,
            strokeWidth = axisStrokeWidth
        )

        //draw Points

        var prevX: Float? = null
        var prevY: Float? = null

        for (dataPoints in input) {
            val x = xCoordinates[dataPoints.x]
            val y = yCoordinates[dataPoints.y]

            drawCircle(
                color = themeColors.secondary,
                radius = pointRadius,
                center = Offset(x!!, y!!)
            )

            if (prevX != null && prevY != null) {
                drawLine(
                    color = themeColors.primary,
                    start = Offset(prevX, prevY),
                    end = Offset(x, y),
                    cap = StrokeCap.Round,
                    strokeWidth = lineStrokeWidth
                )
            }

            prevX = x
            prevY = y
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLineGraphV2() {
    PlannerTheme {
        LineGraphV2(
            modifier = Modifier.size(300.dp),
            input = listOf(
                LineGraphInput(LineGraphInputData(5), LineGraphInputData(10)),
                LineGraphInput(LineGraphInputData(10), LineGraphInputData(5)),
                LineGraphInput(LineGraphInputData(15), LineGraphInputData(15)),
                LineGraphInput(LineGraphInputData(20), LineGraphInputData(20)),
                LineGraphInput(LineGraphInputData(25), LineGraphInputData(25)),
                LineGraphInput(LineGraphInputData(30), LineGraphInputData(30)),
                LineGraphInput(LineGraphInputData(40), LineGraphInputData(30))
            )
        )
    }
}