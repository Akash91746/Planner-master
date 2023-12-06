package com.example.planner.feature_toDoList.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planner.feature_toDoListGroup.domain.models.ToDoListGroup
import java.time.OffsetDateTime

@Entity(
    tableName = "to_do_list",
    foreignKeys = [
        ForeignKey(
            entity = ToDoListGroup::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(
            value = ["groupId"],
            name = "to_do_list_group_index"
        )
    ]
)
data class ToDoList(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var title: String,

    var icon: Int,

    var groupId : Int? = null,

    var taskRemaining: Int = 0,

    val timeStamp: OffsetDateTime = OffsetDateTime.now()
)