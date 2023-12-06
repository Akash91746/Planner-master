package com.example.planner.feature_progress.ui

import androidx.lifecycle.viewModelScope
import com.example.planner.feature_progress.domain.utils.ProgressScreenEvents
import com.example.planner.feature_progress.domain.utils.ProgressScreenState
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.DataType
import com.example.planner.feature_taskRepeat.domain.use_cases.GetRepeatTaskData
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.TaskCount
import com.example.planner.feature_taskRepeat.ui.RepeatTaskViewModel
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.ToDoListTaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProgressScreenViewModel @Inject constructor(
    getRepeatTaskData: GetRepeatTaskData,
    repeatTaskRepository: RepeatTaskRepository,
    toDoListTaskUseCases: ToDoListTaskUseCases,
) : RepeatTaskViewModel(
    getRepeatTaskData,
    repeatTaskRepository,
    toDoListTaskUseCases
) {

    private val _progressScreenState = MutableStateFlow(ProgressScreenState())
    val progressScreenState: StateFlow<ProgressScreenState> = _progressScreenState.asStateFlow()

    private var taskCountJob: Job? = null

    init {
        fetchData(LocalDate.now())

        taskCountJob?.cancel()

        taskCountJob = viewModelScope.launch {
            state.collectLatest {
                calculateTaskCount(it.list)
            }
        }
    }

    private fun calculateTaskCount(list: List<DataType>){
        var totalTask = 0
        var completedTask = 0

        for (item in list) {
            if (item is DataType.RepeatTask) {
                totalTask++
                if (item.repeatTaskWithTask.repeatTask.taskDone) completedTask++
            }
        }

        val taskCount = TaskCount(
            totalTask, completedTask
        )
        _progressScreenState.value = _progressScreenState.value.copy(
            taskCount = taskCount
        )
    }


    fun progressScreenEvent(event: ProgressScreenEvents) {
        when (event) {
            is ProgressScreenEvents.OnChangeDate -> {
                fetchData(event.date)
            }
        }
    }

    private fun fetchData(date: LocalDate) {
        onEvent(RepeatTaskEvents.InitData(date.toString()))
    }

}