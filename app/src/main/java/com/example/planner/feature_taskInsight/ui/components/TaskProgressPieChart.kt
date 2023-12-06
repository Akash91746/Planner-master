package com.example.planner.feature_taskInsight.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planner.feature_taskInsight.domain.utils.PieChartInput
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

@Composable
fun TaskProgressPieChart(
    modifier: Modifier = Modifier,
    data: List<PieChartInput>,
    radius: Float = 200f,
    centerTitleCircleRadius: Float = 100f,
    innerRadius: Float = 130f,
    title: @Composable (BoxScope) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProgressPieChart(
            data = data,
            innerRadius = innerRadius,
            centerTitleCircleRadius = centerTitleCircleRadius,
            title = title,
            modifier = Modifier.size(Dp(radius))
        )

        LazyColumn(
            modifier = Modifier.heightIn(max = 200.dp),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium)
        ) {
            items(data, key = { it.title }) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                ) {
                    Box(modifier = Modifier
                        .padding(end = MaterialTheme.spacing.small)
                        .size(24.dp)
                        .background(item.color, shape = CircleShape)
                    )

                    Text(text = item.title, style = MaterialTheme.typography.caption)
                }
            }
        }
    }
}

@Composable
fun ProgressPieChart(
    modifier: Modifier = Modifier,
    data: List<PieChartInput>,
    centerTitleCircleRadius: Float = 100f,
    innerRadius: Float = 130f,
    title: @Composable (BoxScope) -> Unit,
) {
    val materialColors = MaterialTheme.colors.surface

    Box(
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = modifier) {
            val total = data.sumOf { it.value }

            var startAngle = -90f

            val centerX = size.width / 2
            val centerY = size.height / 2

            val center = Offset(centerX, centerY)

            for (item in data) {
                val percentage = (item.value.toFloat() / total.toFloat()) * 100f
                val sweepAngle = (percentage * 360) / 100

                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                )

                drawContext.canvas.nativeCanvas.apply {
                    val centerAngle = (startAngle + sweepAngle) / 2

                    drawText(
                        "$percentage %",
                        center.x,
                        center.y,
                        Paint().apply {
                            textSize = 13.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.White.toArgb()
                        }
                    )
                }

                startAngle += sweepAngle
            }

            drawCircle(
                color = Color.LightGray.copy(alpha = 0.5f),
                radius = innerRadius,
                center = center,
            )

            drawCircle(
                color = materialColors,
                radius = centerTitleCircleRadius,
                center = center
            )
        }

        title(this)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskProgressPieChart() {
    PlannerTheme {
        TaskProgressPieChart(
            modifier = Modifier.fillMaxWidth(),
            data = listOf(
                PieChartInput(8, "Completed", Color.Green),
                PieChartInput(2, "Remaining", Color.Red)
            ),
            centerTitleCircleRadius = 120f,
            innerRadius = 150f
        ) {
            Text(text = "Task", style = MaterialTheme.typography.subtitle1)
        }
    }
}