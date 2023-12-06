package com.example.planner.feature_progress.ui.components

import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.spacing

/**
    percent: range from 0.0 to 1.0
 */
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    percent: Double,
    primaryColor: Color = MaterialTheme.colors.primary,
    secondaryColor: Color = Color.Gray,
    textColor: Color = MaterialTheme.colors.primary,
    bgColor: Color = MaterialTheme.colors.surface,
    textSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize
) {
    val degree by remember(percent) {
        mutableStateOf( percent * 360f)
    }

    val percentText by remember(percent) {
        mutableStateOf("${(percent * 100).toInt()}%")
    }

    Canvas(
        modifier = modifier
    ) {
        val width = size.width
        val height = size.height
        val radius = height.coerceAtMost(width) / 2

        drawCircle(
            color = secondaryColor,
            radius = radius
        )

        drawArc(
            color = primaryColor,
            startAngle = 270f,
            sweepAngle = degree.toFloat(),
            useCenter = true,
        )

        drawCircle(
            color = bgColor,
            radius = radius * 0.80f
        )

        val textSizeInPx = textSize.toPx()

        val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            this.textSize = textSizeInPx
            color = textColor.toArgb()
            textAlign = Paint.Align.CENTER
            isDither = true
        }

        val yPos = (height / 2 - (textPaint.descent() + textPaint.ascent()) / 2)

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                percentText,
                radius,
                yPos,
                textPaint
            )
        }
    }
}

@Preview
@Composable
fun PreviewProgressIndicator() = PlannerTheme {
    ProgressIndicator(
        modifier = Modifier.size(100.dp, 100.dp),
        percent = 0.0
    )
}

@Composable
fun ArcIcon(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    onClick: ()-> Unit
) {
    val smallSpacing = MaterialTheme.spacing.small
    IconButton(
        onClick = { onClick()}
    ) {
        Box(
            modifier = modifier.size(size).background(
                shape = RoundedCornerShape(
                    bottomStart = 40.dp,
                    topStart = 2.dp,
                    bottomEnd = 2.dp,
                    topEnd = 0.dp
                ), color = Color.LightGray
            )
        ) {

            Icon(
                modifier = Modifier.size(25.dp).padding(start = smallSpacing).align(Alignment.Center),
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colors.secondaryVariant
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewArcIcon() {
    PlannerTheme {
        ArcIcon(onClick = {})
    }
}