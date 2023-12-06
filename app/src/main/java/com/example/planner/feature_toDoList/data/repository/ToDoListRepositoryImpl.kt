package com.example.planner.feature_toDoList.data.repository

import com.example.planner.feature_toDoList.data.data_source.local.ToDoListDao
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.models.ToDoListWithTasks
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import kotlinx.coroutines.flow.Flow

class ToDoListRepositoryImpl(
    private val dao: ToDoListDao,
) : ToDoListRepository {

    override suspend fun insert(toDoList: ToDoList) {
        return dao.insert(toDoList)
    }

    override suspend fun update(toDoList: ToDoList) {
        return dao.update(toDoList)
    }

    override suspend fun delete(toDoList: ToDoList) {
        return dao.delete(toDoList)
    }

    override suspend fun incrementTaskRemaining(id: Int) {
        return dao.incrementTaskRemaining(id)
    }

    override suspend fun decrementTaskRemaining(id: Int) {
        return dao.decrementTaskRemaining(id)
    }

    override suspend fun updateTaskRemaining(id: Int, taskRemaining: Int) {
        return dao.updateTaskRemaining(id, taskRemaining)
    }

    override fun getByListId(listId: Int): Flow<ToDoList> {
        return dao.getToDoListById(listId)
    }

    override fun getAllToDoList(): Flow<List<ToDoList>> {
        return dao.getAllToDoList()
    }

    override fun getToDoLists(groupId: Int?): Flow<List<ToDoList>> {
        groupId?.let {
            return dao.getByGroupId(it)
        }
        return dao.getListWithNoGroup()
    }

    override fun getListWithTasks(listId: Int): Flow<ToDoListWithTasks> {
        return dao.getListWithTasks(listId)
    }
}