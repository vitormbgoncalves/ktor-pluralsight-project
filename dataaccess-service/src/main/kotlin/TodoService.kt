package com.github.vitormbgoncalves.dataaccess.service

import com.github.vitormbgoncalves.todolist.shared.TodoItem

/**
 * Data access methods interface
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 21:33
 */

interface TodoService {
    suspend fun getAll(): List<TodoItem>
    fun getTodo(id: Int): TodoItem
    fun delete(id: Int): Boolean
    fun create(todo: TodoItem): Boolean
    fun update(id: Int, todo: TodoItem): Boolean
    fun loadUserData(userId: String): UserData
}