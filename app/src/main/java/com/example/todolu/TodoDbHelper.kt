package com.example.todolu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDbHelper(context: Context) : SQLiteOpenHelper(context, TodoContractClass.DATABASE_NAME, null, TodoContractClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TodoContractClass.SQL_TABLE_CREATE_CMD)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(TodoContractClass.SQL_TABLE_DELETE_CMD)
        onCreate(db)
    }
}