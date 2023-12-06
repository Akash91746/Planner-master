package com.example.planner.feature_taskRepeat.data.repository

import com.example.planner.feature_taskInsight.domain.models.TaskWithRepeatTasks
import com.example.planner.feature_taskRepeat.data.data_source.RepeatTaskDao
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class RepeatTaskRepositoryImpl(
    private val dao: RepeatTaskDao
) : RepeatTaskRepository {
    override suspend fun insert(repeatTask: RepeatTask): Long {
        return dao.insert(repeatTask)
    }

    override suspend fun update(repeatTask: RepeatTask) {
        return dao.update(repeatTask)
    }

    override suspend fun delete(repeatTask: RepeatTask) {
        return dao.delete(repeatTask)
    }

    override suspend fun getRepeatTaskById(id: Int): RepeatTask? {
        return dao.getRepeatTaskById(id)
    }

    override fun getRepeatTasks(date: LocalDate?): Flow<List<RepeatTask>> {
        date?.let {
            return dao.getRepeatTasks(date.toString())
        }

        return dao.getAllRepeatTasks()
    }

    override fun getRepeatTasksWithTask(date: LocalDate): Flow<List<RepeatTaskWithTask>> {
        return dao.getRepeatTaskWithTask(date.toString())
    }

    override fun getTaskWithRepeatTasks(id: Int): Flow<TaskWithRepeatTasks?> {
        return dao.getTaskWithRepeatTasks(id)
    }

    override suspend fun getRepeatTaskWithTimeStamp(taskId: Int, date: LocalDate): RepeatTask? {
        return dao.getRepeatTaskWithTimeStamp(taskId,date.toString())
    }

    override suspend fun getLatestRepeatTask(taskId: Int): RepeatTask? {
        return dao.getLatestRepeatTask(taskId)
    }
}