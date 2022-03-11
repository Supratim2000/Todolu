package com.example.todolu

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class EditActivity : AppCompatActivity() {

    private lateinit var editTV: TextView
    private lateinit var titleET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var editBT: Button
    private lateinit var todoDbHelper: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        initializeLateinitVariables()

        val intent: Intent = getIntent()
        val prevTitle: String = intent.getStringExtra("titleKey") as String
        val prevDescription: String = intent.getStringExtra("descriptionKey") as String
        val prevTodo: Todo = Todo(prevTitle, prevDescription)
        titleET.setText(prevTitle)
        descriptionET.setText(prevDescription)

        editBT.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val titleString: String = titleET.text.toString().trim()
                val descriptionString: String = descriptionET.text.toString().trim()
                if(!titleString.isEmpty() && !descriptionString.isEmpty()) {
                    val db: SQLiteDatabase = todoDbHelper.writableDatabase
                    val todoInstance: Todo = Todo(titleString, descriptionString)
                    TodoContractClass.updateTodo(db, todoInstance, prevTodo)
                    finish()
                } else {
                    showToast("Input fields can't be empty")
                }
            }
        })

    }

    private fun initializeLateinitVariables(): Unit {
        editTV = findViewById(R.id.editTV)
        titleET = findViewById(R.id.titleET)
        descriptionET = findViewById(R.id.descriptionET)
        editBT = findViewById(R.id.editBT)
        todoDbHelper = TodoDbHelper(this)
    }

    private fun showToast(msg: String): Unit {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}