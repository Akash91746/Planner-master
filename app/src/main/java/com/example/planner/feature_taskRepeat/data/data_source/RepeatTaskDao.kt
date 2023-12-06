package com.example.planner.feature_taskRepeat.data.data_source

import androidx.room.*
import com.example.planner.feature_taskInsight.domain.models.TaskWithRepeatTasks
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import kotlinx.coroutines.flow.Flow

@Dao
interface RepeatTaskDao {

    @Insert
    suspend fun insert(repeatTask: RepeatTask): Long

    @Update
    suspend fun update(repeatTask: RepeatTask)

    @Delete
    suspend fun delete(repeatTask: RepeatTask)

    @Query("Select * from repeat_task where id = (:id)")
    suspend fun getRepeatTaskById(id: Int): RepeatTask?

    @Query("Select * from repeat_task WHERE taskId == (:taskId) AND timeStamp == (:timeStamp)")
    suspend fun getRepeatTaskWithTimeStamp(taskId: Int,timeStamp: String) : RepeatTask?

    @Query("Select * from repeat_task WHERE taskId == (:taskId) order by timeStamp desc limit 1")
    suspend fun getLatestRepeatTask(taskId: Int): RepeatTask?

    @Query("Select * from repeat_task WHERE timeStamp = (:date)")
    fun getRepeatTasks(date: String): Flow<List<RepeatTask>>

    @Query("Select * from repeat_task")
    fun getAllRepeatTasks(): Flow<List<RepeatTask>>

    @Transaction
    @Query("Select * from repeat_task WHERE timeStamp = (:date)")
    fun getRepeatTaskWithTask(date: String): Flow<List<RepeatTaskWithTask>>

    @Transaction
    @Query("Select * from to_do_list_task WHERE id = (:id) LIMIT 1")
    fun getTaskWithRepeatTasks(id: Int) : Flow<TaskWithRepeatTasks?>
}