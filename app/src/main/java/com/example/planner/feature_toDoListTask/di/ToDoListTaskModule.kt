package com.example.planner.feature_toDoListTask.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.RepeatTaskUseCases
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoList.domain.use_cases.EmptyTextValidation
import com.example.planner.feature_toDoListTask.data.repository.ToDoListTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import com.example.planner.feature_toDoListTask.domain.use_cases.GetToDoListTasks
import com.example.planner.feature_toDoListTask.domain.use_cases.GetToDoListTasksData
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases
import com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases.*
import com.example.planner.feature_toDoListTask.domain.use_cases.validaitonUseCases.ValidationUseCases
import com.example.planner.feature_toDoListTask.domain.use_cases.validaitonUseCases.RepeatDialogValidation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ToDoListTaskModule {

    @Provides
    fun providesToDoListTaskRepository(
        database: PlannerDatabase,
    ): ToDoListTaskRepository {
        return ToDoListTaskRepositoryImpl(database.toDoListTaskDao)
    }

    @Provides
    fun providesRepeatDialogValidation(): RepeatDialogValidation {
        return RepeatDialogValidation()
    }

    @Provides
    fun providesValidationUseCases(): ValidationUseCases {
        return ValidationUseCases(
            emptyTextValidation = EmptyTextValidation(),
            repeatDialogValidation = RepeatDialogValidation()
        )
    }

    @Provides
    fun providesToDoListTaskUseCases(
        repository: ToDoListTaskRepository,
        repeatTaskRepository: RepeatTaskRepository,
        taskReminderUseCases: TaskReminderUseCases,
        toDoListRepository: ToDoListRepository,
    ): ToDoListTaskUseCases {
        return ToDoListTaskUseCases(
            deleteToDoListTask = DeleteToDoListTask(
                repository,
                taskReminderUseCases.cancelReminder,
                toDoListRepository = toDoListRepository
            ),
            insertToDoListTask = InsertToDoListTask(
                repository,
                taskReminderUseCases,
                repeatTaskRepository = repeatTaskRepository,
                toDoListRepository = toDoListRepository
            ),
            updateToDoListTask = UpdateToDoListTask(
                repository,
                taskReminderUseCases,
                repeatTaskRepository,
                toDoListRepository = toDoListRepository
            ),
            getTasks = GetToDoListTask(repository)
        )
    }

    @Provides
    fun providesGetToDoListTaskData(
        toDoListRepository: ToDoListRepository,
        repeatTaskUseCases: RepeatTaskUseCases,
    ) = GetToDoListTasksData(
        toDoListRepository,
        repeatTaskUseCases.getRepeatTasksWithTask
    )

    @Provides
    fun providesGetToDoListTasks(
        toDoListRepository: ToDoListRepository,
        taskUseCases: ToDoListTaskUseCases,
    ): GetToDoListTasks {
        return GetToDoListTasks(
            getToDoListTask = taskUseCases.getTasks,
            toDoListRepository = toDoListRepository
        )
    }

}