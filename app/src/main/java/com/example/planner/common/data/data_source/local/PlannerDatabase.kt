package com.example.planner.common.data.data_source.local

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.planner.R
import com.example.planner.common.data.data_source.local.migrations.*
import com.example.planner.common.utils.LocalDateConverter
import com.example.planner.common.utils.LocalTimeConverter
import com.example.planner.feature_toDoList.data.data_source.local.ToDoListDao
import com.example.planner.common.utils.TimeStampConverter
import com.example.planner.feature_quotes.data.data_source.local.QuotesDao
import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_taskRepeat.data.data_source.RepeatTaskDao
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListGroup.data.data_source.local.ToDoListGroupDao
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import com.example.planner.feature_toDoListTask.data.data_source.local.ToDoListTaskDao
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetailsConverter
import com.example.planner.feature_toDoListTask.domain.utils.RepeatModeConverter
import com.example.planner.feature_toDoListTask.domain.utils.WeekDaysConverter

@Database(
    entities = [
        ToDoList::class,
        ToDoListGroup::class,
        ToDoListTask::class,
        RepeatTask::class,
        Quote::class
    ],
    version = 14,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 7,
            to = 8
        ),
        AutoMigration(
            from = 12,
            to = 13,
            spec = PlannerDatabase.Migration12to13::class
        ),
    ]
)
@TypeConverters(
    TimeStampConverter::class,
    RepeatModeConverter::class,
    WeekDaysConverter::class,
    LocalDateConverter::class,
    LocalTimeConverter::class,
    TrackerDetailsConverter::class
)
abstract class PlannerDatabase : RoomDatabase() {

    abstract val toDoListDao: ToDoListDao
    abstract val toDoListGroupDao: ToDoListGroupDao
    abstract val toDoListTaskDao: ToDoListTaskDao
    abstract val repeatTaskDao: RepeatTaskDao
    abstract val quotesDao: QuotesDao

    @DeleteTable("update_tracker")
    class Migration12to13 : AutoMigrationSpec

    companion object {

        @Volatile
        private var INSTANCE: PlannerDatabase? = null

        fun getInstance(context: Context): PlannerDatabase {

            var instance = INSTANCE

            if (instance == null) {
                val databaseName = context.getString(R.string.planner_local_database_name)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlannerDatabase::class.java,
                    databaseName
                )
                    .addMigrations(
                        MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5,
                        MIGRATION_5_6, MIGRATION_6_7, MIGRATION_8_9, MIGRATION_9_10,
                        MIGRATION_10_11, MIGRATION_11_12, MIGRATION_13_14
                    )
                    .build()
                INSTANCE = instance
            }

            return instance
        }

        fun getTestDatabase(context: Context): PlannerDatabase {
            return Room.inMemoryDatabaseBuilder(
                context,
                PlannerDatabase::class.java
            ).allowMainThreadQueries().build()
        }

    }
}