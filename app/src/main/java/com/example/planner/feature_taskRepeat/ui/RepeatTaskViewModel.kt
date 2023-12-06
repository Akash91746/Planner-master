package com.example.planner.feature_taskRepeat.ui

import androidx.lifecycle.viewModelScope
import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.GetRepeatTaskData
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.RepeatTaskScreenState
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.ToDoListTaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
open class RepeatTaskViewModel @Inject constructor(
    private val getRepeatTaskData: GetRepeatTaskData,
    private val repeatTaskRepository: RepeatTaskRepository,
    private val toDoListTaskUseCases: ToDoListTaskUseCases,
) : TrackerTaskViewModel() {

    private val _state = MutableStateFlow(RepeatTaskScreenState())
    val state: StateFlow<RepeatTaskScreenState> = _state.asStateFlow()

    private var fetchJob: Job? = null

    fun onEvent(event: RepeatTaskEvents) {
        when (event) {
            is RepeatTaskEvents.InitData -> {
                event.date?.let {
                    initData(it)
                }
            }
            is RepeatTaskEvents.OnClickCheckBox -> {
                toggleRepeatTaskDoneStatus(event.repeatTask, event.task)
            }
            is RepeatTaskEvents.OnClickFavorite -> {
                toggleFavoriteStatus(event.toDoListTask)
            }
            is RepeatTaskEvents.OnDeleteRepeatTask -> {
                deleteRepeatTask(event.repeatTask)
            }
            is RepeatTaskEvents.OnTrackerTaskEvent -> {
                onEvent(event.event)
            }
        }
    }

    private fun initData(date: String) {
        val parsedDate = LocalDate.parse(date)
        val currentDate = LocalDate.now()

        val appBarTitle = if(parsedDate.equals(currentDate)){
            UiText.StringResource(R.string.today)
        }else if(parsedDate.equals(currentDate.minusDays(1))){
            UiText.StringResource(R.string.yesterday)
        }else {
            UiText.StringResource(R.string.app_name)
        }

        _state.value = _state.value.copy(
            appBarTitle = appBarTitle
        )

        fetchJob?.cancel()

        fetchJob = getRepeatTaskData(parsedDate).onEach {
            _state.value = _state.value.copy(
                list = it.list,
                quote = it.quote
            )
        }.launchIn(viewModelScope)
    }

    private fun toggleRepeatTaskDoneStatus(repeatTask: RepeatTask, toDoListTask: ToDoListTask) {
        val taskStatus = !repeatTask.taskDone
        val updatedTask = repeatTask.copy(taskDone = taskStatus)

        if (taskStatus && toDoListTask.tracker != null) {
            val resultValue = validateValues(repeatTask, toDoListTask.tracker)

            if (resultValue != null) {
                updateRepeatTask(
                    updatedTask.copy(
                        trackerValueOne = resultValue.valueOne,
                        trackerValueTwo = resultValue.valueTwo
                    )
                )
            }

            return
        }

        updateRepeatTask(updatedTask)
    }


    private fun updateRepeatTask(repeatTask: RepeatTask) = viewModelScope.launch {
        repeatTaskRepository.update(repeatTask = repeatTask)
    }

    private fun deleteRepeatTask(repeatTask: RepeatTask) = viewModelScope.launch {
        repeatTaskRepository.delete(repeatTask = repeatTask)
    }

    private fun toggleFavoriteStatus(toDoListTask: ToDoListTask) = viewModelScope.launch {
        toDoListTaskUseCases.updateToDoListTask(toDoListTask.copy(favorite = !toDoListTask.favorite))
    }
}