package com.example.planner.feature_toDoList.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.models.ToDoListWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoList: ToDoList)

    @Update
    suspend fun update(toDoList: ToDoList)

    @Delete
    suspend fun delete(toDoList: ToDoList)

    @Query("UPDATE to_do_list SET taskRemaining = taskRemaining + 1 WHERE id = (:id)")
    suspend fun incrementTaskRemaining(id: Int)

    @Query(
        "UPDATE to_do_list  SET  taskRemaining = CASE \n" +
                "WHEN  taskRemaining > 0 THEN taskRemaining -1\n" +
                "else taskRemaining END\n" +
                "WHERE id == (:id)"
    )
    suspend fun decrementTaskRemaining(id: Int)

    @Query("UPDATE to_do_list SET taskRemaining = (:taskRemaining) WHERE id = (:id)")
    suspend fun updateTaskRemaining(id: Int, taskRemaining: Int)

    @Query("Select * from to_do_list")
    fun getAllToDoList(): Flow<List<ToDoList>>

    @Query("Select * from to_do_list where groupId IS NULL")
    fun getListWithNoGroup(): Flow<List<ToDoList>>

    @Query("Select * from to_do_list where groupId = (:groupId)")
    fun getByGroupId(groupId: Int): Flow<List<ToDoList>>

    @Transaction
    @Query("Select * from to_do_list where id=(:listId) limit 1")
    fun getListWithTasks(listId: Int): Flow<ToDoListWithTasks>

    @Query("Select * from to_do_list where id=(:listId) limit 1")
    fun getToDoListById(listId: Int): Flow<ToDoList>

}