package com.example.planner.common.data_source.local

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.common.data.data_source.local.migrations.MIGRATION_11_12
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "planner_local_database"

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        PlannerDatabase::class.java,
        listOf(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration11To12(){
        var db = helper.createDatabase(DB_NAME,11).apply {
            execSQL("INSERT INTO to_do_list(id,title,icon,groupId,taskRemaining,timeStamp) " +
                    "VALUES(1,'test',0,NULL,0,'TimeStamp');")
            execSQL("INSERT INTO to_do_list_task(listId,title,color,favorite,taskCompleted,timeStamp,repeatMode,weekDays,date,reminderTime,tracker) " +
                    "VALUES(1,'test_task',0,0,0,'timeStamp',NULL,NULL,NULL,NULL,0);")
            close()
        }

        db = helper.runMigrationsAndValidate(DB_NAME,12,true, MIGRATION_11_12)

        db.query("SELECT * from to_do_list_task").apply {
            assertThat(moveToFirst()).isTrue()

            assertThat(getColumnIndex("tracker")).isEqualTo(null)
        }
    }
}