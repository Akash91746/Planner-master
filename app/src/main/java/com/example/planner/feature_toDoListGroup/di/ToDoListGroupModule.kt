package com.example.planner.feature_toDoListGroup.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_toDoListGroup.data.repository.ToDoListGroupRepositoryImpl
import com.example.planner.feature_toDoListGroup.domain.repository.ToDoListGroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ToDoListGroupModule {

    @Provides
    fun providesToDoListGroupRepository(
        database: PlannerDatabase
    ): ToDoListGroupRepository{
        return ToDoListGroupRepositoryImpl(database.toDoListGroupDao)
    }

}