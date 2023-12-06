package com.example.planner.common.di

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.planner.R
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.common.data.services.NotificationServiceImpl
import com.example.planner.common.domain.services.NotificationService
import com.google.firebase.FirebaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesPlannerDatabase(
        @ApplicationContext context: Context,
    ): PlannerDatabase {
        return PlannerDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesNotificationService(
        @ApplicationContext context: Context,
    ): NotificationService {
        return NotificationServiceImpl(context)
    }

    @Provides
    @Singleton
    fun providesWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences{
        val name = context.getString(R.string.private_shared_preference)
        val mode = Context.MODE_PRIVATE

        return context.getSharedPreferences(name,mode)
    }

}