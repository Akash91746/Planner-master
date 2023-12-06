package com.example.planner.feature_toDoList.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.common.utils.AppScreens
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoList.domain.use_cases.EmptyTextValidation
import com.example.planner.feature_toDoList.domain.use_cases.GetToDoListScreenData
import com.example.planner.feature_toDoList.domain.utils.*
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.example.planner.feature_toDoListGroup.domain.repository.ToDoListGroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ToDoListScreenViewModel @Inject constructor(
    private val repository: ToDoListRepository,
    private val toDoListGroupRepository: ToDoListGroupRepository,
    private val getToDoListScreenData: GetToDoListScreenData,
) : ViewModel() {

    private val _state = mutableStateOf(ToDoListScreenState())
    val state: State<ToDoListScreenState> = _state

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            getToDoListScreenData().collectLatest {
                _state.value = _state.value.copy(
                    list = it.items,
                    todayListData = it.todayListData
                )
            }
        }
    }

    fun onEvent(event: ToDoListScreenEvents) {

        when (event) {
            is ToDoListScreenEvents.OnChangeIcon -> {
                updateFormState(_state.value.formState.copy(icon = event.value))
            }
            is ToDoListScreenEvents.OnChangeTitle -> {
                updateFormState(
                    _state.value.formState.copy(
                        title = event.value,
                        titleErrorMessage = null
                    )
                )
            }
            is ToDoListScreenEvents.OnSubmit -> {
                onSubmit()
            }
            is ToDoListScreenEvents.OnChangeFormMode -> {
                val formState = when (event.formMode) {
                    is BottomSheetFormMode.Update.List -> {
                        val toDoList = event.formMode.toDoList
                        FormState(title = toDoList.title, icon = toDoList.icon)
                    }
                    is BottomSheetFormMode.Update.Group -> {
                        val group = event.formMode.toDoListGroup
                        FormState(title = group.title, icon = group.icon)
                    }
                    else -> {
                        FormState(formMode = event.formMode)
                    }
                }

                updateFormState(formState.copy(formMode = event.formMode))

                sendUiEvent(UiEvents.ToggleBottomSheetForm)
            }
            is ToDoListScreenEvents.ResetForm -> {
                updateFormState(FormState())
            }
            is ToDoListScreenEvents.OnDelete -> {
                when (event.type) {
                    is DeleteItemType.List -> deleteToDoList(event.type.toDoList)
                    is DeleteItemType.Group -> deleteToDoListGroup(event.type.toDoListGroup)
                }
            }
            is ToDoListScreenEvents.OnClickToDoList -> {
                navigateToScreen(AppScreens.ToDoListTask(event.toDoList.id))
            }
            is ToDoListScreenEvents.OnClickProgressHeader -> {
                navigateToScreen(AppScreens.ProgressScreen)
            }
            is ToDoListScreenEvents.OnClickTaskInsight -> {
                navigateToScreen(AppScreens.TaskInsightScreen)
            }
            is ToDoListScreenEvents.OnClickTodayTasks -> {
                val date = LocalDate.now().toString()
                navigateToScreen(AppScreens.RepeatTaskScreen(date))
            }
            is ToDoListScreenEvents.OnClickYesterdayTasks -> {
                val date = LocalDate.now().minusDays(1).toString()
                navigateToScreen(AppScreens.RepeatTaskScreen(date))
            }
            is ToDoListScreenEvents.OnClickSettings -> {
                navigateToScreen(AppScreens.SettingScreen)
            }
        }
    }

    private fun navigateToScreen(appScreen: AppScreens){
        sendUiEvent(UiEvents.NavigateTo(appScreen.navPath))
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }

    private fun updateFormState(formState: FormState) {
        _state.value =
            _state.value.copy(formState = formState)
    }

    private fun onSubmit() {
        val formState = _state.value.formState
        val emptyTextValidation = EmptyTextValidation()

        val textError = emptyTextValidation(formState.title)

        if (!textError.successful && textError.errorMessage != null) {
            updateFormState(formState.copy(titleErrorMessage = textError.errorMessage))
            return
        }

        when (formState.formMode) {
            is BottomSheetFormMode.Add.List -> {
                insertToDoList(
                    ToDoList(
                        title = formState.title,
                        icon = formState.icon,
                        groupId = formState.formMode.groupId
                    )
                )
            }
            is BottomSheetFormMode.Add.Group -> {
                insertToDoListGroup(ToDoListGroup(title = formState.title, icon = formState.icon))
            }
            is BottomSheetFormMode.Update.List -> {
                updateToDoList(
                    formState.formMode.toDoList.copy(
                        title = formState.title,
                        icon = formState.icon
                    )
                )
            }
            is BottomSheetFormMode.Update.Group -> {
                updateToDoListGroup(
                    formState.formMode.toDoListGroup.copy(
                        title = formState.title,
                        icon = formState.icon
                    )
                )
            }
            else -> {}
        }

        sendUiEvent(UiEvents.ToggleBottomSheetForm)
    }

    private fun insertToDoList(toDoList: ToDoList) = viewModelScope.launch {
        repository.insert(toDoList)
    }

    private fun updateToDoList(toDoList: ToDoList) = viewModelScope.launch {
        repository.update(toDoList)
    }

    private fun insertToDoListGroup(toDoListGroup: ToDoListGroup) = viewModelScope.launch {
        toDoListGroupRepository.insert(toDoListGroup)
    }

    private fun updateToDoListGroup(toDoListGroup: ToDoListGroup) = viewModelScope.launch {
        toDoListGroupRepository.update(toDoListGroup)
    }

    private fun deleteToDoList(toDoList: ToDoList) = viewModelScope.launch {
        repository.delete(toDoList)
    }

    private fun deleteToDoListGroup(toDoListGroup: ToDoListGroup) = viewModelScope.launch {
        toDoListGroupRepository.delete(toDoListGroup)
    }
}