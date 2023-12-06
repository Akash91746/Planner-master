package com.example.planner.feature_taskRepeat.di

import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.data.repository.RepeatTaskRepositoryImpl
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepeatTaskModule {
    @Provides
    fun providesRepeatTaskModuleRepository(
        database: PlannerDatabase,
    ): RepeatTaskRepository {
        return RepeatTaskRepositoryImpl(database.repeatTaskDao)
    }

    @Provides
    fun providesRepeatTaskUseCases(
        repository: RepeatTaskRepository
    ): RepeatTaskUseCases {
        return RepeatTaskUseCases(
            getRepeatTasksWithTask = GetRepeatTasksWithTask(repository)
        )
    }
}