package com.example.planner.feature_toDoListTask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.R
import com.example.planner.common.utils.AppScreens
import com.example.planner.common.utils.UiText
import com.example.planner.feature_toDoList.domain.utils.DefaultToDoLists
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.GetToDoListTasks
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.ToDoListTaskUseCases
import com.example.planner.feature_toDoListTask.domain.use_cases.validaitonUseCases.ValidationUseCases
import com.example.planner.feature_toDoListTask.domain.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ToDoListTaskViewModel @Inject constructor(
    private val toDoListTaskUseCases: ToDoListTaskUseCases,
    private val getToDoListTasks: GetToDoListTasks,
    private val validationUseCases: ValidationUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(ToDoListTaskState())
    val state = _state.asStateFlow()

    private var initDataJob: Job? = null

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private fun initializeData(listId: Int) {
        initDataJob?.cancel()

        initDataJob = if (listId == DefaultToDoLists.FAVORITES_LIST.id) {
            _state.value = _state.value.copy(appBarTitle = UiText.StringResource(R.string.favorites))
            getToDoListTasks.getFavoriteTasks()
        } else {
            getToDoListTasks(listId)
        }.onEach {

            var appBarTitle = _state.value.appBarTitle

            if(it.toDoList != null) {
                appBarTitle = UiText.DynamicText(
                    it.toDoList.title.replaceFirstChar { title ->
                        if (title.isLowerCase()) title.titlecase(Locale.ROOT) else title.toString()
                    }
                )
            }

            _state.value = _state.value.copy(
                toDoList = it.toDoList,
                listTasks = it.items,
                appBarTitle = appBarTitle
            )

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ToDoListTaskEvents) {
        when (event) {
            is ToDoListTaskEvents.Init -> {
                initializeData(event.listId)
            }
            is ToDoListTaskEvents.OnClickBack -> {
                sendUiEvents(UiEvents.Navigate())
            }
            is ToDoListTaskEvents.OnEditTask -> {
                handleUpdateToDoListTask(task = event.task)
                sendUiEvents(UiEvents.ToggleBottomSheet)
            }
            is ToDoListTaskEvents.OnClickAdd -> {
                _state.value = _state.value.copy(
                    selectedTask = null,
                    formState = ToDoListTaskFormState(),
                )
                sendUiEvents(UiEvents.ToggleBottomSheet)
            }
            is ToDoListTaskEvents.OnDeleteTask -> {
                deleteToDoListTask(event.value)
            }
            is ToDoListTaskEvents.OnToDoListTaskFormEvents -> {
                handleTaskFormEvents(event.formEvent)
            }
            is ToDoListTaskEvents.OnClickTaskInsight -> {
                sendUiEvents(UiEvents.Navigate(AppScreens.TaskInsight(event.taskId).navPath))
            }
            is ToDoListTaskEvents.ToggleTaskDone -> {
                val updatedTask = event.task.copy(
                    taskCompleted = !event.task.taskCompleted
                )
                updateToDoListTask(updatedTask,event.task)
            }
            is ToDoListTaskEvents.ToggleFavoriteStatus -> {
                val task = event.task.copy(
                    favorite = !event.task.favorite
                )
                updateToDoListTask(task)
            }
        }
    }

    private fun handleTaskFormEvents(event: ToDoListTaskFormEvents) {
        val formState = _state.value.formState
        when (event) {
            is ToDoListTaskFormEvents.OnChangeTitle -> {
                updateFormState(formState.copy(title = event.value, titleErrorMessage = null))
            }
            is ToDoListTaskFormEvents.OnChangeColor -> {
                updateFormState(formState.copy(color = event.value))
            }
            is ToDoListTaskFormEvents.OnRepeatDialogEvent -> {
                handleRepeatDialogEvents(event.event)
            }
            is ToDoListTaskFormEvents.OnSubmit -> {
                onSubmit(_state.value.formState)
            }
            is ToDoListTaskFormEvents.ResetForm -> {
                updateFormState(ToDoListTaskFormState())
            }
            is ToDoListTaskFormEvents.ToggleShowRepeatDialog -> {
                val repeatDialogState =
                    if (!formState.showRepeatDialog && formState.repeatDialogState.repeatMode != null) {
                        RepeatDialogState()
                    } else {
                        formState.repeatDialogState
                    }
                updateFormState(
                    formState.copy(
                        showRepeatDialog = !formState.showRepeatDialog,
                        repeatDialogState = repeatDialogState
                    )
                )
            }
            is ToDoListTaskFormEvents.ToggleTrackerDialog -> {
                val trackerState = formState.trackerDialogState

                val trackerDialogOpen = !formState.showTrackerDialog

                if (trackerState.selected && trackerDialogOpen) {
                    updateTrackerDialogState(TrackerDialogState())
                    return
                }

                updateFormState(formState.copy(showTrackerDialog = trackerDialogOpen))
            }
            is ToDoListTaskFormEvents.OnTrackerDialogEvent -> {
                handleTrackerDialogEvent(event.event)
            }
            is ToDoListTaskFormEvents.OnChangeReminderTime -> {
                updateFormState(formState.copy(reminder = event.value))
            }
            is ToDoListTaskFormEvents.HideKeyboard -> {
                sendUiEvents(UiEvents.HideKeyboard)
            }
        }
    }

    private fun handleRepeatDialogEvents(event: RepeatDialogEvents) {
        val formState = _state.value.formState
        val repeatDialogState = formState.repeatDialogState

        when (event) {
            is RepeatDialogEvents.OnChangeDate -> {
                updateRepeatDialogState(
                    repeatDialogState.copy(
                        date = event.date,
                        errorMessage = null
                    )
                )
            }
            is RepeatDialogEvents.OnChangeRepeatMode -> {
                updateRepeatDialogState(
                    repeatDialogState.copy(
                        repeatMode = event.repeatMode,
                        errorMessage = null
                    )
                )
            }
            is RepeatDialogEvents.OnChangeWeekDaySelection -> {
                val value = onChangeWeekDaySelection(event.weekDay)
                value?.let {
                    updateRepeatDialogState(
                        repeatDialogState.copy(
                            weekDays = value,
                            errorMessage = null
                        )
                    )
                }
            }
            is RepeatDialogEvents.OnSubmit -> {
                handleSubmitRepeatDialog()
            }
            is RepeatDialogEvents.OnClickClear -> {
                updateRepeatDialogState(RepeatDialogState())
            }
        }
    }

    private fun handleTrackerDialogEvent(event: TrackerDialogEvents) {
        val dialogState = _state.value.formState.trackerDialogState

        when (event) {
            is TrackerDialogEvents.OnChangeTitleOne -> {
                updateTrackerDialogState(
                    dialogState.copy(
                        titleOne = event.value,
                        titleOneErrorMessage = null
                    )
                )
            }
            is TrackerDialogEvents.OnChangeTitleTwo -> {
                updateTrackerDialogState(
                    dialogState.copy(
                        titleTwo = event.value,
                        titleTwoErrorMessage = null
                    )
                )
            }
            is TrackerDialogEvents.ToggleEnableTitleTwo -> {
                updateTrackerDialogState(dialogState.copy(titleTwoEnabled = !dialogState.titleTwoEnabled))
            }
            is TrackerDialogEvents.OnSubmit -> {
                submitTrackerDialog(_state.value.formState)
            }
            is TrackerDialogEvents.OnClear -> {
                updateTrackerDialogState(TrackerDialogState())
            }
        }
    }

    private fun submitTrackerDialog(formState: ToDoListTaskFormState) {
        val trackerDialogState = formState.trackerDialogState

        updateTrackerDialogState(
            state = trackerDialogState.copy(
                titleOneErrorMessage = null,
                titleTwoErrorMessage = null
            )
        )

        val titleOneResult = validationUseCases.emptyTextValidation(trackerDialogState.titleOne)

        if (!titleOneResult.successful) {
            updateTrackerDialogState(
                state = trackerDialogState.copy(
                    titleOneErrorMessage = titleOneResult.errorMessage
                )
            )
            return
        }

        val titleTwoResult = if (trackerDialogState.titleTwoEnabled)
            validationUseCases.emptyTextValidation(trackerDialogState.titleTwo)
        else null

        if (titleTwoResult != null && !titleTwoResult.successful) {
            updateTrackerDialogState(
                state = trackerDialogState.copy(
                    titleTwoErrorMessage = titleTwoResult.errorMessage
                )
            )

            return
        }

        updateFormState(
            formState = formState.copy(
                showTrackerDialog = false,
                trackerDialogState = trackerDialogState.copy(selected = true)
            )
        )
    }

    private fun handleUpdateToDoListTask(task: ToDoListTask) {
        val formState = ToDoListStateConverter.toToDoListTaskFormState(task)

        _state.value = _state.value.copy(
            selectedTask = task,
            formState = formState
        )
    }

    private fun toggleShowRepeatDialog() {
        handleTaskFormEvents(ToDoListTaskFormEvents.ToggleShowRepeatDialog)
    }

    private fun handleSubmitRepeatDialog() {
        val formState = _state.value.formState
        val repeatDialogState = formState.repeatDialogState

        updateRepeatDialogState(repeatDialogState.copy(errorMessage = null))

        val result = validationUseCases.repeatDialogValidation(repeatDialogState)

        if (!result.successful) {
            updateRepeatDialogState(repeatDialogState.copy(errorMessage = result.errorMessage))
            return
        }

        toggleShowRepeatDialog()
    }

    private fun updateFormState(formState: ToDoListTaskFormState) {
        _state.value = _state.value.copy(formState = formState)
    }

    private fun updateRepeatDialogState(state: RepeatDialogState) {
        val formState = _state.value.formState
        _state.value =
            _state.value.copy(formState = formState.copy(repeatDialogState = state))
    }

    private fun updateTrackerDialogState(state: TrackerDialogState) {
        val formState = _state.value.formState

        _state.value = _state.value.copy(formState = formState.copy(trackerDialogState = state))
    }

    private fun onChangeWeekDaySelection(weekDay: WeekDay): List<WeekDay>? {
        val weekDays = _state.value.formState.repeatDialogState.weekDays

        val index = weekDays.indexOf(weekDay)

        if (index != -1) {
            val newList = mutableListOf<WeekDay>()

            newList.addAll(weekDays)

            newList[index] = weekDay.copy(selected = !weekDay.selected)

            return newList
        }

        return null
    }

    private fun sendUiEvents(events: UiEvents) = viewModelScope.launch {
        _uiEvents.send(events)
    }

    private fun onSubmit(formState: ToDoListTaskFormState) {

        val toDoList = state.value.toDoList ?: return

        updateFormState(
            formState.copy(
                titleErrorMessage = null
            )
        )

        val result = validationUseCases.emptyTextValidation(formState.title)

        if (!result.successful) {
            _state.value = _state.value.copy(
                formState = formState.copy(
                    titleErrorMessage = result.errorMessage ?: "Required * "
                )
            )
            return
        }

        val newTask = ToDoListStateConverter.toToDoListTask(_state.value, toDoList.id)
        val selectedTask = _state.value.selectedTask

        if (selectedTask == null) {
            insertToDoListTask(newTask)
        } else {
            updateToDoListTask(newTask, selectedTask)
        }

        sendUiEvents(UiEvents.ToggleBottomSheet)
    }

    private fun insertToDoListTask(task: ToDoListTask) = viewModelScope.launch {
        toDoListTaskUseCases.insertToDoListTask(task)
    }

    private fun updateToDoListTask(newTask: ToDoListTask, oldTask: ToDoListTask? = null) =
        viewModelScope.launch {
            toDoListTaskUseCases.updateToDoListTask(newTask, oldTask)
        }

    private fun deleteToDoListTask(task: ToDoListTask) = viewModelScope.launch {
        toDoListTaskUseCases.deleteToDoListTask(task)
    }

}