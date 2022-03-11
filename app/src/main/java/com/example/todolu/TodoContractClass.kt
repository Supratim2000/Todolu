package com.example.todolu

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

object TodoContractClass {
    val DATABASE_NAME: String = "Todo.db"
    val DATABASE_VERSION: Int = 1
    val TABLE_NAME: String = "todos"

    object Column {
        val COLUMN_ID: String = "_ID"
        val COLUMN_TITLE: String = "title"
        val COLUMN_DESCRIPTION: String = "description"
    }

    val SQL_TABLE_CREATE_CMD: String = """$TABLECREATE TABLE IF NOT EXISTS _NAME (
        ${Column.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.COLUMN_TITLE} TEXT NOT NULL,
        ${Column.COLUMN_DESCRIPTION} TEXT NOT NULL
        )
    """.trimMargin()

    val SQL_TABLE_DELETE_CMD: String = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun deleteTodo(db: SQLiteDatabase, title: String): Unit {
        val DELETE_CMD = "DELETE FROM $TABLE_NAME WHERE ${Column.COLUMN_TITLE} = \"$title\";"
        try {
            db.execSQL(DELETE_CMD)
        } finally {
            db.close()
        }
    }

    fun insertTodo(db: SQLiteDatabase, todo: Todo): Boolean {
        try{
            val titleString: String = todo.getTitle()
            val descriptionString: String = todo.getContent()
            val todoCv: ContentValues = ContentValues()
            todoCv.put(Column.COLUMN_TITLE, titleString)
            todoCv.put(Column.COLUMN_DESCRIPTION, descriptionString)
            val result: Boolean = db.insert(TABLE_NAME, null, todoCv) != -1L

            return result
        } finally {
            db.close()
        }
    }

    fun updateTodo(db: SQLiteDatabase, todo: Todo, prevTodo: Todo): Unit {
        val prevTitleString: String = prevTodo.getTitle()
        val prevDecriptionString: String = prevTodo.getContent()
        val titleString: String = todo.getTitle()
        val descriptionString: String = todo.getContent()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE ${Column.COLUMN_TITLE} = \"$prevTitleString\";", null)
        try {
            cursor.moveToFirst()
            val rowIndex: Int = cursor.getInt(cursor.getColumnIndex(Column.COLUMN_ID))
            Log.v("test", "$rowIndex is the _id")
            val todoCv: ContentValues = ContentValues()
            todoCv.put(Column.COLUMN_TITLE, titleString)
            todoCv.put(Column.COLUMN_DESCRIPTION, descriptionString)
            db.update(TABLE_NAME, todoCv, "${Column.COLUMN_ID} = ?", arrayOf(rowIndex.toString()))
        } finally {
            db.close()
            cursor.close()
        }
    }

    fun getAllTodos(db: SQLiteDatabase): ArrayList<Todo> {
        val todolist: ArrayList<Todo> = ArrayList()
        val projection = arrayOf(Column.COLUMN_ID, Column.COLUMN_TITLE, Column.COLUMN_DESCRIPTION)
        val cursor: Cursor = db.query(TABLE_NAME, projection,null,null,null,null,null)
        try {
            while(cursor.moveToNext()) {
                val titleString: String = cursor.getString(cursor.getColumnIndex(Column.COLUMN_TITLE))
                val descriptionString: String = cursor.getString(cursor.getColumnIndex(Column.COLUMN_DESCRIPTION))
                val currentTodo: Todo = Todo(titleString, descriptionString)
                todolist.add(currentTodo)
            }
            return todolist
        } finally {
            db.close()
            cursor.close()
        }
    }
}