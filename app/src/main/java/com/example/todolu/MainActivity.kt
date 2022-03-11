package com.example.todolu

import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), TodoItemClicked, SwipedLeft, SwipedRight {

    override fun onBackPressed() {
        showExitAlertDialogue()
    }

    private lateinit var headingTV: TextView
    private lateinit var noTaskTV: TextView
    private lateinit var addFab: FloatingActionButton
    private lateinit var todoRV: RecyclerView
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoArray: ArrayList<Todo>
    private lateinit var todoDbHelper: SQLiteOpenHelper

    private val TEXT_ANIMATION_DURATION: Long = 1500
    private val TEXT_ANIMATION_REPEATE: Int = 1

    override fun onStart() {
        super.onStart()
        refreshTodos()
        visibilityChange()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeLateinitVariables()
        textEffect()

        noTaskTV.visibility = INVISIBLE

        todoRV.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        todoAdapter = TodoAdapter(this, this, this, todoArray)
        todoRV.adapter = todoAdapter

        refreshTodos()

        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(Swipe(todoAdapter, this, this))
        itemTouchHelper.attachToRecyclerView(todoRV)

        addFab.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                //showToast("Fab clicked")
                val adderIntent: Intent = Intent(this@MainActivity, AdderActivity::class.java)
                startActivity(adderIntent)
            }
        })
    }

    //Use this function if need to add more features
    override fun onTodoItemClicked(item: Todo, position: Int) {
        //showToast("Item Clicked")
    }

    override fun onSwipeLeft(position: Int) {
        showDeleteAlertDialogue(position)
    }

    override fun onSwipeRight(position: Int) {
        val editorIntent: Intent = Intent(this, EditActivity::class.java)
        editorIntent.putExtra("titleKey", todoArray[position].getTitle())
        editorIntent.putExtra("descriptionKey", todoArray[position].getContent())
        startActivity(editorIntent)
    }

    private fun deleteTodo(position: Int): Unit {
        val item: Todo = todoArray[position]
        showToast("Item deleted successfully")
        val todoDb: SQLiteDatabase = todoDbHelper.writableDatabase
        TodoContractClass.deleteTodo(todoDb, item.getTitle())
        refreshTodos()
        visibilityChange()
    }

    private fun refreshTodos(): Unit {
        val todoDb: SQLiteDatabase = todoDbHelper.readableDatabase
        val tempTodo: ArrayList<Todo> = TodoContractClass.getAllTodos(todoDb)
        todoArray.clear()
        todoArray.addAll(tempTodo)
        tempTodo.clear()
        todoAdapter.notifyDataSetChanged()
    }

    private fun visibilityChange(): Unit {
        if(todoArray.size == 0) {
            todoRV.visibility = View.GONE
            noTaskTV.visibility = View.VISIBLE
        } else {
            todoRV.visibility = View.VISIBLE
            noTaskTV.visibility = View.GONE
        }
    }

    private fun initializeLateinitVariables(): Unit {
        headingTV = findViewById(R.id.headingTV)
        noTaskTV = findViewById(R.id.noTaskTV)
        addFab = findViewById(R.id.addFab)
        todoRV = findViewById(R.id.todoRV)
        todoArray = ArrayList()
        todoDbHelper = TodoDbHelper(this)
    }

    private fun textEffect(): Unit {
        YoYo.with(Techniques.BounceInUp)
            .duration(TEXT_ANIMATION_DURATION)
            .repeat(TEXT_ANIMATION_REPEATE)
            .playOn(headingTV)
    }

    private fun showToast(msg: String): Unit {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun showExitAlertDialogue(): Unit {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setMessage("Do you really want to exit the application?")
        builder.setPositiveButton("Yes") { dialogInterface, which -> finish()}
        builder.setNegativeButton("No") {dialogInterface, which -> todoAdapter.notifyDataSetChanged() }
        builder.setCancelable(false)
        builder.setIcon(R.drawable.ic_baseline_close)

        val exitDialogue: AlertDialog = builder.create()
        exitDialogue.show()
    }

    private fun showDeleteAlertDialogue(position: Int): Unit {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setMessage("Do you what to delete this todo from your list?")
        builder.setPositiveButton("Yes") { dialogInterface, which -> deleteTodo(position)}
        builder.setNegativeButton("No") {dialogInterface, which -> refreshTodos() }
        builder.setCancelable(false)
        builder.setIcon(R.drawable.ic_baseline_delete_forever_red)

        val exitDialogue: AlertDialog = builder.create()
        exitDialogue.show()
    }
}