package com.example.planner.feature_toDoListTask.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_toDoList.data.data_source.local.ToDoListDao
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoListGroup.data.data_source.local.ToDoListGroupDao
import com.example.planner.feature_toDoListTask.data.data_source.local.ToDoListTaskDao
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat

@SmallTest
@HiltAndroidTest
class ToDoListTaskRepositoryTestType {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var toDoListDao: ToDoListDao

    private lateinit var toDoListTaskDao: ToDoListTaskDao

    @Inject
    lateinit var database: PlannerDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        toDoListDao = database.toDoListDao
        toDoListTaskDao = database.toDoListTaskDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun taskInsertionAndTriggerIncrementTaskRemaining() = runBlocking {
        val toDoList = ToDoList(id = 1, groupId = null, title = "Test List", icon = 1)

        toDoListDao.insert(toDoList)

        val toDoListTask = ToDoListTask(listId = 1, title = "Test List Task")

        toDoListTaskDao.insert(toDoListTask)

        val toDoLists = toDoListDao.getAllToDoList().firstOrNull()

        toDoLists?.let { list ->

            assertThat(list).isNotEmpty()

            assertThat(list[0].taskRemaining).isEqualTo(1)
        }

        assertThat(toDoList).isNotNull()
    }

    @Test
    fun taskDeletionAndTriggerDecrementTaskRemaining() = runBlocking {
        val toDoList = ToDoList(id = 1, title = "Test List", icon = 1)

        toDoListDao.insert(toDoList)

        val toDoListTask = ToDoListTask(listId = 1, title = "Test List Task")

        toDoListTaskDao.insert(toDoListTask)

        toDoListTaskDao.delete(toDoListTask)

        val toDoLists = toDoListDao.getAllToDoList().firstOrNull()

        toDoLists?.let { list ->

            assertThat(list).isNotEmpty()

            assertThat(list[0].taskRemaining).isEqualTo(0)
        }

        assertThat(toDoList).isNotNull()
    }

    @Test
    fun taskUpdateAndUpdateRemainingTasks() = runBlocking {
        val toDoList = ToDoList(id = 1, groupId = null, title = "Test List", icon = 1)

        toDoListDao.insert(toDoList)

        val toDoListTask = ToDoListTask(listId = 1, title = "Test List Task")

        toDoListTaskDao.insert(toDoListTask)

        toDoListTaskDao.update(toDoListTask.copy(taskCompleted = true))

        val toDoLists = toDoListDao.getAllToDoList().firstOrNull()

        toDoLists?.let { list ->

            assertThat(list).isNotEmpty()

            assertThat(list[0].taskRemaining).isEqualTo(0)
        }

        assertThat(toDoList).isNotNull()
    }
}