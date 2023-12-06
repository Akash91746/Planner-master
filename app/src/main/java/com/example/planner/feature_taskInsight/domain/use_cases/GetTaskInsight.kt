package com.example.planner.feature_taskInsight.domain.use_cases

import androidx.compose.ui.graphics.Color
import com.example.planner.feature_taskInsight.domain.utils.LineChartData
import com.example.planner.feature_taskInsight.domain.utils.LineGraphInput
import com.example.planner.feature_taskInsight.domain.utils.LineGraphInputData
import com.example.planner.feature_taskInsight.domain.utils.PieChartInput
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class GetTaskInsight(
    private val repeatTaskRepository: RepeatTaskRepository,
) {
    operator fun invoke(
        taskId: Int,
        fromDate: LocalDate,
        toDate: LocalDate,
    ): Flow<ResponseData?> {

        return repeatTaskRepository.getTaskWithRepeatTasks(taskId).map { taskWithRepeatTask ->
            if (taskWithRepeatTask == null) return@map null

            var taskList = taskWithRepeatTask.list

            if (fromDate.isBefore(toDate)) {
                taskList = taskList.filter { repeatTask ->
                    repeatTask.timeStamp.isAfter(fromDate.minusDays(1)) &&
                            repeatTask.timeStamp.isBefore(toDate.plusDays(1))
                }
            }

            val taskCount = TaskCount(
                totalTask = taskList.size,
                completedTask = taskList.count { task -> task.taskDone }
            )

            val pieChartInputList = listOf(
                PieChartInput(
                    taskCount.completedTask,
                    "Completed",
                    Color.Green
                ),
                PieChartInput(taskCount.remainingTask, "Remaining", Color.Red)
            )

            val list = taskList.sortedBy { it.timeStamp }

            val lineChartData = getLineChartData(list, taskWithRepeatTask.task)

            return@map ResponseData(
                taskWithRepeatTask.task,
                list,
                pieChartInputList,
                lineChartData = lineChartData
            )
        }
    }

    private fun getLineChartData(
        list: List<RepeatTask>,
        task: ToDoListTask,
    ): LineChartData? {

        if (task.tracker == null) return null

        val map1 = LinkedHashMap<LocalDate, Double>()
        val map2 = LinkedHashMap<LocalDate, Double>()

        list.forEach {
            if (it.taskDone) {
                if (it.trackerValueOne != null) map1[it.timeStamp] = it.trackerValueOne
                if (it.trackerValueTwo != null) map2[it.timeStamp] = it.trackerValueTwo
            }
        }

        val initialOffset = 10000f

        val xValuesToDates = map1.keys.mapIndexed { index, localDate ->
           (index + initialOffset) to localDate
        }.toMap()

        if(map1.size <= 1 || (map2.size <= 1 && task.tracker.titleTwo != null)) return null

        val chartEntryModel = if (task.tracker.titleTwo != null) {
            val x2ValuesToDates = map2.keys.mapIndexed { index, localDate ->
                (index + initialOffset) to localDate
            }.toMap()

            entryModelOf(
                xValuesToDates.keys.zip(map1.values, ::entryOf),
                x2ValuesToDates.keys.zip(map2.values, ::entryOf)
            )
        } else {
           entryModelOf(xValuesToDates.keys.zip(map1.values, ::entryOf))
        }

        val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")
        val horizontalAxisValueFormatter =
            AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
                (xValuesToDates[value]
                    ?: LocalDate.ofEpochDay(value.toLong())).format(dateTimeFormatter)
            }

        return LineChartData(
            chartEntryModel = chartEntryModel,
            horizontalAxisValueFormatter = horizontalAxisValueFormatter
        )
    }

    data class ResponseData(
        val task: ToDoListTask,
        val list: List<RepeatTask>,
        val pieChartInputList: List<PieChartInput>,
        val lineGraphInputList: List<LineGraphInput>? = null,
        val lineChartData: LineChartData? = null,
    )
}