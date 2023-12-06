package com.example.planner.feature_taskRepeat.ui

import androidx.lifecycle.ViewModel
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskEvents
import com.example.planner.feature_taskRepeat.domain.utils.TrackerTaskState
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

open class TrackerTaskViewModel : ViewModel() {

    private val _trackerTaskState = MutableStateFlow(TrackerTaskState())
    val trackerTaskState: StateFlow<TrackerTaskState> = _trackerTaskState.asStateFlow()

    fun onEvent(event: TrackerTaskEvents) {
        when (event) {
            is TrackerTaskEvents.OnChangeValueOne -> {
                updateTrackerState(
                    _trackerTaskState.value.copy(
                        valueOne = event.value,
                        errorMessage = null
                    )
                )
            }
            is TrackerTaskEvents.OnChangeValueTwo -> {
                updateTrackerState(
                    _trackerTaskState.value.copy(
                        valueTwo = event.value,
                        errorMessage = null
                    )
                )
            }
            is TrackerTaskEvents.OnClickExpand -> {
                toggleRepeatTaskExpand(event.repeatTask)
            }
        }
    }

    fun validateValues(repeatTask: RepeatTask,trackerDetails: TrackerDetails) : TrackerValues? {
        val state = _trackerTaskState.value
        val currentExpandedTask = state.expandedTask
        val isTaskSame = repeatTask.id == currentExpandedTask?.id
        val secondTitleEnabled = trackerDetails.titleTwo != null

        if (!isTaskSame){

            val valueOne = repeatTask.trackerValueOne
            val valueTwo = repeatTask.trackerValueTwo

            if(valueOne == null || (secondTitleEnabled && valueTwo == null)){
                toggleRepeatTaskExpand(repeatTask,"Please fill all required values *")
                return null
            }

            return TrackerValues(valueOne, valueTwo)
        }

        val valueOne = state.valueOne
        val valueTwo = state.valueTwo

        if (isTextEmpty(valueOne) || (secondTitleEnabled && isTextEmpty(valueTwo))){
            _trackerTaskState.value = _trackerTaskState.value.copy(
                errorMessage = "Please fill all required values *"
            )
            return null
        }

        return TrackerValues(valueOne.toDoubleOrNull(), valueTwo = valueTwo.toDoubleOrNull())
    }

    private fun updateTrackerState(state: TrackerTaskState) {
        _trackerTaskState.value = state
    }

    private fun toggleRepeatTaskExpand(repeatTask: RepeatTask, errorMessage: String? = null) {
        val currentExpandedTask = _trackerTaskState.value.expandedTask

        val expandedTask = if (repeatTask == currentExpandedTask) {
            null
        } else {
            repeatTask
        }

        updateTrackerState(
            _trackerTaskState.value.copy(
                expandedTask = expandedTask,
                valueOne = expandedTask?.trackerValueOne?.toString() ?: "",
                valueTwo = expandedTask?.trackerValueTwo?.toString() ?: "",
                errorMessage = errorMessage
            )
        )
    }

    private fun isTextEmpty(value: String) = value.trim().isEmpty()

    data class TrackerValues(
        val valueOne: Double?,
        val valueTwo: Double?
    )

}