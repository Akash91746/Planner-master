package com.example.planner.feature_toDoListTask.data.repository

import com.example.planner.feature_toDoListTask.data.data_source.local.ToDoListTaskDao
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import kotlinx.coroutines.flow.Flow

class ToDoListTaskRepositoryImpl(
    private val dao: ToDoListTaskDao
) : ToDoListTaskRepository {

    override suspend fun insert(toDoListTask: ToDoListTask): Long {
        return dao.insert(toDoListTask)
    }

    override suspend fun update(toDoListTask: ToDoListTask) {
        return dao.update(toDoListTask)
    }

    override suspend fun delete(toDoListTask: ToDoListTask) {
        return dao.delete(toDoListTask)
    }

    override suspend fun getTaskByTimeStamp(timeStamp: String): ToDoListTask? {
        return dao.getByTimeStamp(timeStamp)
    }

    override suspend fun getTaskById(id: Int): ToDoListTask? {
        return dao.getTaskById(id)
    }

    override fun getTaskByListId(listId: Int): Flow<List<ToDoListTask>> {
        return dao.getListTasks(listId)
    }

    override fun getAllToDoListTask(): Flow<List<ToDoListTask>> {
        return dao.getAllToDoListTask()
    }

    override fun getRepeatingTasks(): Flow<List<ToDoListTask>> {
        return dao.getRepeatingTasks()
    }

    override fun getFavoriteTasks(): Flow<List<ToDoListTask>> {
        return dao.getFavoriteTasks()
    }
}