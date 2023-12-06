package com.example.planner.feature_taskInsight.ui

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.example.planner.feature_taskInsight.domain.use_cases.GetTaskInsight
import com.example.planner.feature_taskInsight.domain.utils.*
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.ui.TrackerTaskViewModel
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.GetToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.ToDoListTaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskInsightViewModel @Inject constructor(
    private val taskUseCases: ToDoListTaskUseCases,
    private val getTaskInsight: GetTaskInsight,
    private val repeatTaskRepository: RepeatTaskRepository,
    private val sharedPreferences: SharedPreferences,
) : TrackerTaskViewModel() {

    private val _state = MutableStateFlow(TaskInsightState())
    val state = _state.asStateFlow()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var job: Job? = null
    private var autoCompleteTaskJob: Job? = null

    init {
        fetchAutoCompleteData()
    }

    private fun fetchAutoCompleteData() {
        autoCompleteTaskJob?.cancel()

        autoCompleteTaskJob = viewModelScope.launch {
            taskUseCases.getTasks(GetToDoListTask.Filter.RepeatingTasks).collectLatest { tasks ->
                val prevAutoCompleteState = _state.value.autoCompleteState
                val newAutoCompleteState = AutoCompleteState(
                    startItems = tasks.asAutoCompleteEntities { item, query ->
                        item.title.contains(query, true)
                    }
                )

                newAutoCompleteState.isSearching = prevAutoCompleteState.isSearching

                _state.value = _state.value.copy(
                    autoCompleteState = newAutoCompleteState
                )
            }
        }
    }

    private fun initData(taskId: Int, fromDate: LocalDate, toDate: LocalDate) {
        job?.cancel()

        sharedPreferences.edit().putInt(PREFERENCE_TASK_ID, taskId).apply()

        job = getTaskInsight(taskId, fromDate, toDate).onEach { responseData ->
            responseData?.let {
                _state.value = _state.value.copy(
                    pieCharInputList = it.pieChartInputList,
                    task = it.task,
                    list = it.list,
                    fromDate = fromDate,
                    toDate = toDate,
                    lineChartData = it.lineChartData
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: TaskInsightEvents) {
        when (event) {
            is TaskInsightEvents.InitData -> {
                val taskId = if (event.taskId == null) {
                    val prevTaskId = sharedPreferences.getInt(PREFERENCE_TASK_ID, Int.MAX_VALUE)

                    if (prevTaskId == Int.MAX_VALUE)
                        null
                    else prevTaskId
                } else {
                    event.taskId
                }

                if (taskId != null)
                    initData(taskId, _state.value.fromDate, _state.value.toDate)
            }
            is TaskInsightEvents.OnChangeToDate -> {
                onChangeDate(_state.value.fromDate, event.date)
            }
            is TaskInsightEvents.OnChangeFromDate -> {
                onChangeDate(event.date, _state.value.toDate)
            }
            is TaskInsightEvents.OnDelete -> {
                deleteRepeatTask(event.repeatTask)
            }
            is TaskInsightEvents.OnChangeTaskInsightType -> {
                _state.value = _state.value.copy(
                    currentTab = event.type
                )
            }
            is TaskInsightEvents.OnChangeSearchQuery -> {
                val query = event.query

                val autoCompleteState = _state.value.autoCompleteState
                autoCompleteState.isSearching = true
                autoCompleteState.filter(query)

                _state.value = _state.value.copy(
                    searchQuery = event.query,
                    autoCompleteState = autoCompleteState
                )
            }
            is TaskInsightEvents.OnClearSearchQuery -> {
                val autoCompleteState = _state.value.autoCompleteState
                autoCompleteState.isSearching = false
                autoCompleteState.filter("")

                _state.value = _state.value.copy(
                    searchQuery = "",
                    autoCompleteState = autoCompleteState
                )
            }
            is TaskInsightEvents.OnSelectTask -> {
                val autoCompleteState = _state.value.autoCompleteState
                autoCompleteState.isSearching = false

                _state.value = _state.value.copy(
                    autoCompleteState = autoCompleteState,
                    task = event.task,
                    searchQuery = ""
                )

                initData(
                    taskId = event.task.id,
                    fromDate = _state.value.fromDate,
                    toDate = _state.value.toDate
                )
            }
            is TaskInsightEvents.OnClickNewEntry -> {
                sendUiEvents(UiEvents.OpenDatePicker)
            }
            is TaskInsightEvents.AddNewEntry -> {
                addNewEntry(event.date)
            }
            is TaskInsightEvents.OnClickCheckBox -> {
                handleOnClickCheckBox(repeatTask = event.repeatTask)
            }
            is TaskInsightEvents.OnClickFavorite -> {
                val task = event.toDoListTask
                updateToDoListTask(task.copy(favorite = !task.favorite))
            }
        }
    }

    private fun sendUiEvents(uiEvent: UiEvents) = viewModelScope.launch {
        _uiEvents.send(uiEvent)
    }

    private fun handleOnClickCheckBox(repeatTask: RepeatTask) {
        val status = !repeatTask.taskDone
        val toDoListTask = state.value.task ?: return
        val updatedTask = repeatTask.copy(taskDone = status)

        if (status && toDoListTask.tracker != null) {
            val result = validateValues(repeatTask, toDoListTask.tracker)

            if (result != null) {
                updateRepeatTask(
                    repeatTask = updatedTask.copy(
                        trackerValueOne = result.valueOne,
                        trackerValueTwo = result.valueTwo
                    )
                )
                sendUiEvents(UiEvents.HideKeyboard)
            }

            return
        }

        updateRepeatTask(updatedTask)
    }

    private fun addNewEntry(date: LocalDate) = viewModelScope.launch {
        _state.value.task?.let { task ->
            val result =
                repeatTaskRepository.getRepeatTaskWithTimeStamp(taskId = task.id, date = date)
            if (result == null) {
                repeatTaskRepository.insert(RepeatTask(taskId = task.id, timeStamp = date))
            } else {
                sendUiEvents(UiEvents.ShowSnackBar("$date Entry Already Exists!"))
            }
        }
    }

    private fun onChangeDate(fromDate: LocalDate, toDate: LocalDate) {
        val state = _state.value

        state.task?.let {
            initData(it.id, fromDate, toDate)
        }
    }

    private fun deleteRepeatTask(repeatTask: RepeatTask) = viewModelScope.launch {
        repeatTaskRepository.delete(repeatTask = repeatTask)
    }

    private fun updateRepeatTask(repeatTask: RepeatTask) = viewModelScope.launch {
        repeatTaskRepository.update(repeatTask = repeatTask)
    }

    private fun updateToDoListTask(task: ToDoListTask) = viewModelScope.launch {
        taskUseCases.updateToDoListTask(task)
    }

    companion object {
        const val PREFERENCE_TASK_ID = "task_insight_preference_id"
    }
}