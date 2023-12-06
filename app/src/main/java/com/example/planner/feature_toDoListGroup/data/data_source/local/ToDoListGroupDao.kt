package com.example.planner.feature_toDoListGroup.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.planner.feature_toDoListGroup.domain.models.GroupWithItems
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoListGroup: ToDoListGroup)

    @Update
    suspend fun update(toDoListGroup: ToDoListGroup)

    @Delete
    suspend fun delete(toDoListGroup: ToDoListGroup)

    @Query("Select * from to_do_list_group")
    fun getAllToDoListGroup() : Flow<List<ToDoListGroup>>

    @Transaction
    @Query("Select * from to_do_list_group")
    fun getGroupWithItems() : Flow<List<GroupWithItems>>

    @Transaction
    @Query("Select * from to_do_list_group where id = (:groupId) LIMIT 1")
    fun getGroupWithItems(groupId: Int) : Flow<GroupWithItems>
}