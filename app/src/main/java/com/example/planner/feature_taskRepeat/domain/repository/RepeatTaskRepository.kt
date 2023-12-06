package com.example.planner.feature_taskRepeat.domain.repository

import com.example.planner.feature_taskInsight.domain.models.TaskWithRepeatTasks
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RepeatTaskRepository {

    suspend fun insert(repeatTask: RepeatTask): Long

    suspend fun update(repeatTask: RepeatTask)

    suspend fun delete(repeatTask: RepeatTask)

    suspend fun getRepeatTaskById(id: Int): RepeatTask?

    suspend fun getRepeatTaskWithTimeStamp(taskId: Int, date: LocalDate) : RepeatTask?

    suspend fun getLatestRepeatTask(taskId: Int): RepeatTask?

    fun getRepeatTasks(date: LocalDate? =null): Flow<List<RepeatTask>>

    fun getRepeatTasksWithTask(date: LocalDate): Flow<List<RepeatTaskWithTask>>

    fun getTaskWithRepeatTasks(id: Int): Flow<TaskWithRepeatTasks?>

}