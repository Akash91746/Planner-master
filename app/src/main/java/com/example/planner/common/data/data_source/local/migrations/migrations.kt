package com.example.planner.common.data.data_source.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.beginTransaction()

        try {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `new_to_do_list_task` " +
                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`listId` INTEGER NOT NULL, " +
                        "`title` TEXT NOT NULL, " +
                        "`color` INTEGER NOT NULL, " +
                        "`favorite` INTEGER NOT NULL, " +
                        "`taskCompleted` INTEGER NOT NULL, " +
                        "`timeStamp` TEXT NOT NULL, " +
                        "FOREIGN KEY(`listId`) REFERENCES `to_do_list`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE);"
            )

            db.execSQL("INSERT INTO new_to_do_list_task SELECT id, listId, title,color,favorite, taskCompleted, timeStamp FROM to_do_list_task;")

            db.execSQL("DROP TABLE to_do_list_task;")

            db.execSQL("ALTER TABLE new_to_do_list_task RENAME TO to_do_list_task;")

            db.execSQL("CREATE INDEX IF NOT EXISTS `task_index_listId` ON `to_do_list_task` (`listId`)")

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.beginTransaction()
        try {

            db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN repeatMode TEXT DEFAULT NULL;")

            db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN weekDays TEXT DEFAULT NULL;")

            db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN tracker INTEGER DEFAULT NULL;")

            db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN date TEXT DEFAULT NULL;")

            db.setTransactionSuccessful()

        } finally {
            db.endTransaction()
        }
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS update_tracker(" +
                    "date TEXT NOT NULL PRIMARY KEY," +
                    "initialized INTEGER NOT NULL)"
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS repeat_task(" +
                    "id INTEGER NOT NULL, " +
                    "taskId INTEGER NOT NULL," +
                    "taskDone INTEGER NOT NULL, " +
                    "timeStamp TEXT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (taskId) REFERENCES to_do_list_task(id) ON DELETE CASCADE)"
        )

        db.execSQL("CREATE INDEX IF NOT EXISTS repeat_task_index_taskId ON repeat_task(taskId)")
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.beginTransaction()

        try {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `new_to_do_list_task` " +
                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`listId` INTEGER NOT NULL, " +
                        "`title` TEXT NOT NULL, " +
                        "`color` INTEGER NOT NULL, " +
                        "`favorite` INTEGER NOT NULL, " +
                        "`taskCompleted` INTEGER NOT NULL, " +
                        "`timeStamp` TEXT NOT NULL," +
                        "repeatMode TEXT," +
                        "weekDays TEXT," +
                        "date TEXT, " +
                        "FOREIGN KEY(`listId`) REFERENCES `to_do_list`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE);"
            )

            db.execSQL("INSERT INTO new_to_do_list_task SELECT id, listId, title,color,favorite, taskCompleted, timeStamp,repeatMode,weekDays,date FROM to_do_list_task;")

            db.execSQL("DROP TABLE to_do_list_task;")

            db.execSQL("ALTER TABLE new_to_do_list_task RENAME TO to_do_list_task;")

            db.execSQL("CREATE INDEX IF NOT EXISTS `task_index_listId` ON `to_do_list_task` (`listId`)")

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS tracker_task(" +
                        "id INTEGER NOT NULL," +
                        "taskId INTEGER NOT NULL," +
                        "titleOne TEXT NOT NULL," +
                        "valueOne INTEGER NOT NULL, " +
                        "titleTwo TEXT," +
                        "valueTwo INTEGER," +
                        "PRIMARY KEY(id)," +
                        "FOREIGN KEY (taskId) REFERENCES to_do_list_task(id) ON DELETE CASCADE);"
            )

            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS tracker_task_index_taskId ON tracker_task(taskId);")


            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN tracker INTEGER NOT NULL DEFAULT 0")

        try {
            db.beginTransaction()

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS new_tracker_task(" +
                        "taskId INTEGER NOT NULL," +
                        "titleOne TEXT NOT NULL," +
                        "valueOne INTEGER," +
                        "titleTwo TEXT," +
                        "valueTwo INTEGER," +
                        "PRIMARY KEY(taskId)," +
                        "FOREIGN KEY (taskId) REFERENCES to_do_list_task(id) ON DELETE CASCADE);"
            )

            db.execSQL("INSERT INTO new_tracker_task SELECT taskId,titleOne,valueOne,titleTwo,valueTwo FROM tracker_task;")

            db.execSQL("DROP TABLE tracker_task;")

            db.execSQL("ALTER TABLE new_tracker_task RENAME TO tracker_task;")

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.beginTransaction()

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS new_tracker_task(" +
                        "taskId INTEGER NOT NULL," +
                        "titleOne TEXT NOT NULL," +
                        "valueOne REAL," +
                        "titleTwo TEXT," +
                        "valueTwo REAL," +
                        "PRIMARY KEY(taskId)," +
                        "FOREIGN KEY (taskId) REFERENCES to_do_list_task(id) ON DELETE CASCADE);"
            )

            db.execSQL("INSERT INTO new_tracker_task SELECT taskId,titleOne,valueOne,titleTwo,valueTwo FROM tracker_task;")

            db.execSQL("DROP TABLE tracker_task;")

            db.execSQL("ALTER TABLE new_tracker_task RENAME TO tracker_task;")


            db.execSQL(
                "CREATE TABLE IF NOT EXISTS new_repeat_task(" +
                        "id INTEGER NOT NULL, " +
                        "taskId INTEGER NOT NULL," +
                        "taskDone INTEGER NOT NULL, " +
                        "timeStamp TEXT NOT NULL," +
                        "trackerValueOne REAL DEFAULT NULL," +
                        "trackerValueTwo REAL DEFAULT NULL," +
                        "PRIMARY KEY (id)," +
                        "FOREIGN KEY (taskId) REFERENCES to_do_list_task(id) ON DELETE CASCADE);"
            )

            db.execSQL("INSERT INTO new_repeat_task SELECT id,taskId,taskDone,timeStamp,null,null from repeat_task;")

            db.execSQL("DROP TABLE repeat_task;")

            db.execSQL("ALTER TABLE new_repeat_task RENAME TO repeat_task;")

            db.execSQL("CREATE INDEX IF NOT EXISTS repeat_task_index_taskId ON repeat_task(taskId)")

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}

val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE to_do_list_task ADD COLUMN reminderTime TEXT DEFAULT NULL;")
    }
}

val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS quote(" +
                    "date TEXT NOT NULL," +
                    "quote TEXT NOT NULL," +
                    "author TEXT NOT NULL," +
                    "PRIMARY KEY (date));"
        )
    }
}

val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {

        try {
            db.beginTransaction()

            val tableName = "new_to_do_list_task"
            val oldTableName = "to_do_list_task"

            db.execSQL("CREATE TABLE IF NOT EXISTS `${tableName}` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`listId` INTEGER NOT NULL, `title` TEXT NOT NULL, " +
                    "`color` INTEGER NOT NULL, " +
                    "`favorite` INTEGER NOT NULL, " +
                    "`taskCompleted` INTEGER NOT NULL, " +
                    "`timeStamp` TEXT NOT NULL, " +
                    "`repeatMode` TEXT, " +
                    "`weekDays` TEXT, " +
                    "`date` TEXT, " +
                    "`tracker` TEXT , " +
                    "`reminderTime` TEXT, " +
                    "FOREIGN KEY(`listId`) REFERENCES `to_do_list`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")


            db.execSQL("INSERT INTO $tableName SELECT id,listId,title,color,favorite,taskCompleted,timeStamp,repeatMode,weekDays,date,reminderTime,null from $oldTableName")

            db.execSQL("DROP TABLE $oldTableName")

            db.execSQL("ALTER TABLE $tableName RENAME TO $oldTableName")

            db.execSQL("CREATE INDEX IF NOT EXISTS `task_index_listId` ON `to_do_list_task` (`listId`)")

            db.execSQL("DROP TABLE tracker_task")
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

    }
}

val MIGRATION_13_14  = object: Migration(13,14){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE UNIQUE INDEX unique_task_by_day_index ON repeat_task (taskId,timeStamp);")
    }

}
