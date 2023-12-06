package com.example.planner.feature_toDoList.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.feature_toDoList.data.data_source.local.ToDoListDao
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class ToDoListRepositoryImplTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val item = ToDoList(id = 1, title = "List Test", icon = PlannerIcons.DEFAULT)

    lateinit var repository: ToDoListDao

    @Inject
    lateinit var database: PlannerDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        repository = database.toDoListDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert() = runBlocking {

        repository.insert(item)

        val list = repository.getAllToDoList().firstOrNull()

        assertThat(list).isNotEmpty()
    }

    @Test
    fun delete() = runBlocking {
        repository.insert(item)

        val list = repository.getAllToDoList().firstOrNull()

        assertThat(list).isNotEmpty()
        assertThat(list).isNotNull()

        list?.let {
            val item = it.first()
            repository.delete(item)
        }

        val newList = repository.getAllToDoList().firstOrNull()

        assertThat(newList).isEmpty()
    }

    @Test
    fun getByGroupId() = runBlocking {
        repository.insert(item)

        val list = repository.getListWithNoGroup().firstOrNull()

        assertThat(list).isNotNull()

        assertThat(list).isNotEmpty()
    }

    @Test
    fun updateTaskRemaining() = runBlocking {
        repository.insert(item)

        repository.incrementTaskRemaining(item.id)

        var toDoList = repository.getToDoListById(item.id).firstOrNull()

        assertThat(toDoList).isNotNull()

        assertThat(toDoList?.taskRemaining).isEqualTo(1)

        repository.decrementTaskRemaining(item.id)

        toDoList = repository.getToDoListById(item.id).first()

        assertThat(toDoList.taskRemaining).isEqualTo(0)
    }
}