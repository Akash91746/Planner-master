package com.example.planner.feature_toDoListGroup.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.feature_toDoList.data.data_source.local.ToDoListDao
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListGroup.data.data_source.local.ToDoListGroupDao
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class ToDoListGroupRepositoryTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var toDoListDao: ToDoListDao
    lateinit var toDoListGroupDao: ToDoListGroupDao

    @Inject
    lateinit var database: PlannerDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        toDoListDao = database.toDoListDao
        toDoListGroupDao = database.toDoListGroupDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertToDoListGroup() = runBlocking {
        val group = ToDoListGroup(id = 1,title = "Test Group", icon = 1)

        toDoListGroupDao.insert(group)

        val item = ToDoList(id = 1,groupId = 1,title = "List Test", icon = PlannerIcons.DEFAULT)

        toDoListDao.insert(item)

        val groupWithItems = toDoListGroupDao.getGroupWithItems(1).firstOrNull()

        assertThat(groupWithItems).isNotNull()

        assertThat(groupWithItems?.items).contains(item)
    }

}