package com.example.planner.common.data.data_source.local.utils

enum class DBDataType(val dataType: String) {
    INTEGER("INTEGER"),
    TEXT("TEXT"),
    REAL("REAL")
}


sealed class DataMetaData{
    object NotNull: DataMetaData()

    data class Null(val defaultNull: Boolean = false): DataMetaData()
}

data class PrimaryKey(
    val autoIncrement: Boolean = true
)

data class DBData(
    val name: String,
    val dataType: DBDataType,
    val metaData: DataMetaData = DataMetaData.NotNull,
    val primaryKey: PrimaryKey? = null
)
