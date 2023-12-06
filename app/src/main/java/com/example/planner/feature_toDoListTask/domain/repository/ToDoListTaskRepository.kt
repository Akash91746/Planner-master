package com.example.planner.feature_toDoListTask.domain.repository

import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import kotlinx.coroutines.flow.Flow

interface ToDoListTaskRepository {

    suspend fun insert(toDoListTask: ToDoListTask): Long

    suspend fun update(toDoListTask: ToDoListTask)

    suspend fun delete(toDoListTask: ToDoListTask)

    suspend fun getTaskByTimeStamp(timeStamp: String) : ToDoListTask?

    suspend fun getTaskById(id: Int): ToDoListTask?

    fun getTaskByListId(listId: Int): Flow<List<ToDoListTask>>

    fun getAllToDoListTask(): Flow<List<ToDoListTask>>

    fun getRepeatingTasks(): Flow<List<ToDoListTask>>

    fun getFavoriteTasks(): Flow<List<ToDoListTask>>
}