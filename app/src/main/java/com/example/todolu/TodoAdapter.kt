package com.example.todolu

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(activity: Activity,context: Context, listener: TodoItemClicked, todolist: ArrayList<Todo>): RecyclerView.Adapter<viewHolder>() {
    private val activity: Activity
    private val context: Context
    private val listener: TodoItemClicked
    private val todolist: ArrayList<Todo>
    init {
        this.activity = activity
        this.context = context
        this.listener = listener
        this.todolist = todolist
    }

    fun getActivity(): Activity = this.activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.todo_layout, parent, false)
        val itemViewHolder = viewHolder(itemView)
        itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                listener.onTodoItemClicked(todolist[itemViewHolder.adapterPosition], itemViewHolder.adapterPosition)
            }
        })
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem: Todo = todolist[position]
        holder.titleTV.text = currentItem.getTitle()
        holder.contentTV.text = currentItem.getContent()
    }

    override fun getItemCount(): Int = this.todolist.size
}

class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleTV: TextView = itemView.findViewById(R.id.titleTV)
    val contentTV: TextView = itemView.findViewById(R.id.contentTV)
}

interface TodoItemClicked {
    fun onTodoItemClicked(item: Todo, position: Int)
}