package com.example.planner.common.data.data_source.local.utils

class DBHelper {

    fun getCreateTableQuery(
        tableName: String,
        values: List<DBData>,
        additionalQuery: String? = null,
    ): String {

        val stringBuilder = StringBuilder("CREATE TABLE IF NOT EXISTS $tableName(")

        for (value in values) {
            stringBuilder.append("${value.name} ${value.dataType.dataType} ")
            if (value.primaryKey != null) {
                stringBuilder.append("PRIMARY KEY ")
                if(value.primaryKey.autoIncrement){
                    stringBuilder.append("AUTOINCREMENT ")
                }
            }
            when(value.metaData){
                is DataMetaData.Null -> {
                    stringBuilder.append("NULL ")
                    if(value.metaData.defaultNull){
                        stringBuilder.append("DEFAULT NULL ")
                    }
                    stringBuilder.append(", ")
                }
                is DataMetaData.NotNull -> {
                    stringBuilder.append("NOT NULL, ")
                }
            }
        }

        additionalQuery?.let {
            stringBuilder.append("$additionalQuery")
        }

        stringBuilder.append(");")

        return stringBuilder.toString()
    }

}