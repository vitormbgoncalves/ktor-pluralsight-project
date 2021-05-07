package com.github.vitormbgoncalves.dataaccess.service

import com.github.vitormbgoncalves.repository.TodoListRepository
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import java.time.LocalDate

/**
 * Data access methods interface implementation
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 21:50
 */

class TodoServiceDBCall(val todoListRepository: TodoListRepository) : TodoService {

    override suspend fun getAll(): List<TodoItem> {
        return todos
    }

    override fun getTodo(id: Int): TodoItem {
        return todos.filter { it.id == id }.single()
    }

    override fun create(todo: TodoItem): Boolean {
        val todo = TodoItem(todo.id, todo.title, todo.detail, todo.assignedTo, todo.dueDate, todo.importance)
        return todos.add(todo)
    }

    override fun update(id: Int, todo: TodoItem): Boolean {
        todos.removeIf { it.id == id }
        val todo = TodoItem(id, todo.title, todo.detail, todo.assignedTo, todo.dueDate, todo.importance)
        return todos.add(todo)
    }

    override fun delete(id: Int): Boolean {
        return todos.removeIf { it.id == id }
    }

    override fun loadUserData(userId: String): UserData {
        return UserData(userId)
    }
}

/*
instances of TodoItem
 */
val todo1 = TodoItem(
    1,
    "Add database processing 1",
    "Add backend support to this code",
    "Vitor",
    LocalDate.of(2021, 5, 3),
    Importance.HIGH
)

val todo2 = TodoItem(
    2,
    "Add database processing 2",
    "Add backend support to this code",
    "Vitor",
    LocalDate.of(2021, 5, 3),
    Importance.HIGH
)

val todo3 = TodoItem(
    3,
    "Add database processing 3",
    "Testing implementation",
    "Vitor",
    LocalDate.of(2021, 5, 3),
    Importance.HIGH
)

val todo4 = TodoItem(
    4,
    "Add database processing 4",
    "Add backend OAuth2  authentication",
    "Vitor",
    LocalDate.of(2018, 12, 18),
    Importance.HIGH
)

val todos = mutableListOf(todo1, todo2, todo3, todo4)