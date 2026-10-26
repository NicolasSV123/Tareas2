package com.nicolas.tareas.data

data class Task (
    val id: Int,
    var title: String,
    var done: Boolean
) {
    companion object {
        const val TABLE_NAME            = "Tasks"
        const val COLUMN_NAME_ID        = "id"
        const val COLUMN_NAME_TITLE     = "title"
        const val COLUMN_NAME_DONE      = "done"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ("+
                    "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "$COLUMN_NAME_TITLE TEXT,"+
                    "$COLUMN_NAME_DONE BOOLEAN)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}

