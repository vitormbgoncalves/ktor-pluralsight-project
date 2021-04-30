package com.github.vitormbgoncalves.todolist.web.todolist.web

import com.github.vitormbgoncalves.todolist.shared.TodoItem

/**
 * View class
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 20:17
 */

data class TodoVM(private val todos: List<TodoItem>, private val backgroundColor: String) {
    val todoItems = todos
    val userName = "Vitor Goncalves"
    val background = backgroundColor
}