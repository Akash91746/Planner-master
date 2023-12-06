package com.example.planner.feature_reminder.di

import android.content.Context
import com.example.planner.common.data.services.AlarmServiceImpl
import com.example.planner.common.domain.services.AlarmService
import com.example.planner.feature_reminder.data.services.AlarmSchedulerImpl
import com.example.planner.feature_reminder.domain.services.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun providesAlarmService(
        @ApplicationContext context: Context,
    ): AlarmService {
        return AlarmServiceImpl(context)
    }

    @Provides
    @Singleton
    fun providesAlarmScheduler(
        alarmService: AlarmService
    ) : AlarmScheduler {
        return AlarmSchedulerImpl(alarmService)
    }

}