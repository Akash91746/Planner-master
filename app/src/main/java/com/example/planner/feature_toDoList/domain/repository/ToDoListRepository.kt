package com.example.planner.feature_toDoList.domain.repository

import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.models.ToDoListWithTasks
import kotlinx.coroutines.flow.Flow

interface ToDoListRepository {

    suspend fun insert(toDoList: ToDoList)

    suspend fun update(toDoList: ToDoList)

    suspend fun delete(toDoList: ToDoList)

    suspend fun incrementTaskRemaining(id: Int)

    suspend fun decrementTaskRemaining(id: Int)

    suspend fun updateTaskRemaining(id: Int,taskRemaining: Int)

    fun getByListId(listId: Int): Flow<ToDoList>

    fun getAllToDoList(): Flow<List<ToDoList>>

    fun getToDoLists(groupId: Int?): Flow<List<ToDoList>>

    fun getListWithTasks(listId: Int): Flow<ToDoListWithTasks>
}