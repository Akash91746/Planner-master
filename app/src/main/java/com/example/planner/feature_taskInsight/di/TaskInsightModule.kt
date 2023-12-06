package com.example.planner.feature_taskInsight.di

import com.example.planner.feature_taskInsight.domain.use_cases.GetTaskInsight
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TaskInsightModule {

    @Provides
    fun providesGetTaskInsightData(
        repeatTaskRepository: RepeatTaskRepository,
    ): GetTaskInsight {
        return GetTaskInsight(repeatTaskRepository)
    }
}