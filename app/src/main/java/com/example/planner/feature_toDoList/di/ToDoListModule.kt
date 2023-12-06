package com.example.planner.feature_toDoList.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.RepeatTaskCount
import com.example.planner.feature_toDoList.data.repository.ToDoListRepositoryImpl
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoList.domain.use_cases.GetToDoListScreenData
import com.example.planner.feature_toDoListGroup.domain.repository.ToDoListGroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ToDoListModule {

    @Provides
    fun providesToDoListRepository(database: PlannerDatabase): ToDoListRepository {
        return ToDoListRepositoryImpl(database.toDoListDao)
    }

    @Provides
    fun providesGetToDoListScreenData(
        toDoListRepository: ToDoListRepository,
        toDoListGroupRepository: ToDoListGroupRepository,
        repeatTaskRepository: RepeatTaskRepository
    ): GetToDoListScreenData {
        return GetToDoListScreenData(toDoListRepository, toDoListGroupRepository, RepeatTaskCount(repeatTaskRepository))
    }
}