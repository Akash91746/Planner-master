package com.example.planner.feature_toDoListGroup.data.repository

import com.example.planner.feature_toDoListGroup.data.data_source.local.ToDoListGroupDao
import com.example.planner.feature_toDoListGroup.domain.models.GroupWithItems
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.example.planner.feature_toDoListGroup.domain.repository.ToDoListGroupRepository
import kotlinx.coroutines.flow.Flow

class ToDoListGroupRepositoryImpl(
    private val dao: ToDoListGroupDao
) : ToDoListGroupRepository {

    override suspend fun insert(toDoListGroup: ToDoListGroup) {
        return dao.insert(toDoListGroup)
    }

    override suspend fun update(toDoListGroup: ToDoListGroup) {
        return dao.update(toDoListGroup)
    }

    override suspend fun delete(toDoListGroup: ToDoListGroup) {
        return dao.delete(toDoListGroup)
    }

    override fun getAllToDoListGroup(): Flow<List<ToDoListGroup>> {
        return dao.getAllToDoListGroup()
    }

    override fun getGroupWithItems(): Flow<List<GroupWithItems>> {
        return dao.getGroupWithItems()
    }

    override fun getGroupWithItems(groupId: Int): Flow<GroupWithItems> {
        return dao.getGroupWithItems(groupId)
    }
}