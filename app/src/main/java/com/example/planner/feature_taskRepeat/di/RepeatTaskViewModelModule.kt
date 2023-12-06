package com.example.planner.feature_taskRepeat.di

import com.example.planner.feature_quotes.domain.useCases.QuotesUseCases
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_taskRepeat.domain.use_cases.GetRepeatTaskData
import com.example.planner.feature_toDoList.domain.use_cases.EmptyTextValidation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepeatTaskViewModelModule {

    @Provides
    fun providesGetRepeatTaskData(
        repository: RepeatTaskRepository,
        quotesUseCases: QuotesUseCases,
    ): GetRepeatTaskData {
        return GetRepeatTaskData(repository, quotesUseCases)
    }

    @Provides
    fun providesEmptyTextValidation(): EmptyTextValidation {
        return EmptyTextValidation()
    }

}