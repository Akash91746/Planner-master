package com.example.planner.feature_toDoListGroup.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(
    tableName = "to_do_list_group"
)
data class ToDoListGroup(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var title: String,

    var icon: Int,

    val timeStamp: OffsetDateTime = OffsetDateTime.now()

)
