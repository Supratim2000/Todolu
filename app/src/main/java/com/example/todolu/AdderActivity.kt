package com.example.todolu

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class AdderActivity : AppCompatActivity() {

    private lateinit var addBT: TextView
    private lateinit var titleET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var todoDbHelper: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adder)

        initializeLateinitVariables()

        addBT.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val titleString: String = titleET.text.toString().trim()
                val descriptionString: String = descriptionET.text.toString().trim()
                if(!titleString.isEmpty() && !descriptionString.isEmpty()) {
                    val todoDb: SQLiteDatabase = todoDbHelper.writableDatabase
                    val todoInstance: Todo = Todo(titleString, descriptionString)
                    //showToast("$titleString -> $descriptionString")
                    val result = TodoContractClass.insertTodo(todoDb,todoInstance)
                    if(result == true) {
                        showToast("Entry successfully added")
                    } else {
                        showToast("Failed to add entry")
                    }
                    finish()
                } else {
                    showToast("Input fields can't be empty")
                }
            }
        })

    }

    private fun initializeLateinitVariables(): Unit {
        addBT = findViewById(R.id.addBT)
        titleET = findViewById(R.id.titleET)
        descriptionET = findViewById(R.id.descriptionET)
        todoDbHelper = TodoDbHelper(this)
    }

    private fun showToast(msg: String): Unit {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}