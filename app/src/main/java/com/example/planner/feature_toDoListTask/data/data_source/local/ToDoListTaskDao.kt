package com.example.planner.feature_toDoListTask.data.data_source.local

import androidx.room.*
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListTaskDao {

    @Insert
    suspend fun insert(toDoListTask: ToDoListTask): Long

    @Update
    suspend fun update(toDoListTask: ToDoListTask)

    @Delete
    suspend fun delete(toDoListTask: ToDoListTask)

    @Query("Select * from to_do_list_task")
    fun getAllToDoListTask(): Flow<List<ToDoListTask>>

    @Query("Select * from to_do_list_task where favorite = 1")
    fun getFavoriteTasks(): Flow<List<ToDoListTask>>

    @Query("Select * from to_do_list_task where listId = (:listId)")
    fun getListTasks(listId: Int): Flow<List<ToDoListTask>>

    @Query("Select * from to_do_list_task where timeStamp = (:timeStamp) limit 1")
    suspend fun getByTimeStamp(timeStamp: String): ToDoListTask?

    @Query("Select * from to_do_list_task where id = (:id) limit 1")
    suspend fun getTaskById(id: Int): ToDoListTask?

    @Query("SELECT * FROM to_do_list_task WHERE repeatMode IS NOT NULL")
    fun getRepeatingTasks(): Flow<List<ToDoListTask>>
}