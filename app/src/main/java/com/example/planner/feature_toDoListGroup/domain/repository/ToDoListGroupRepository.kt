package com.example.planner.feature_toDoListGroup.domain.repository

import com.example.planner.feature_toDoListGroup.domain.models.GroupWithItems
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import kotlinx.coroutines.flow.Flow

interface ToDoListGroupRepository {

    suspend fun insert(toDoListGroup: ToDoListGroup)

    suspend fun update(toDoListGroup: ToDoListGroup)

    suspend fun delete(toDoListGroup: ToDoListGroup)

    fun getAllToDoListGroup(): Flow<List<ToDoListGroup>>

    fun getGroupWithItems(): Flow<List<GroupWithItems>>

    fun getGroupWithItems(groupId:Int): Flow<GroupWithItems>
}