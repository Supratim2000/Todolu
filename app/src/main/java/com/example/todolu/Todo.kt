package com.example.todolu

class Todo(title: String, content: String) {
    private val title: String
    private val content: String

    init {
        this.title = title
        this.content = content
    }

    fun getTitle(): String = this.title
    fun getContent(): String = this.content
}