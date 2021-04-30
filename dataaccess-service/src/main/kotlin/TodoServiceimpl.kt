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

// instances of TodoItem
val todo1 = TodoItem(
    1,
    "Add database processing 1",
    "Add backend support to this code",
    "Kevin",
    LocalDate.of(2018, 12, 18),
    Importance.HIGH
)

val todo2 = TodoItem(
    2,
    "Add database processing 2",
    "Add backend support to this code",
    "Kevin",
    LocalDate.of(2018, 12, 18),
    Importance.HIGH
)

val todos = mutableListOf(todo1, todo2)

class TodoServiceimpl(val todoListRepository: TodoListRepository) : TodoService {
    override fun update(id: Int, todo: TodoItem): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadUserData(userId: String): UserData {
        TODO("Not yet implemented")
    }

    override fun create(todo: TodoItem): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAll(): List<TodoItem> {
        return todos
    }

    override fun getTodo(id: Int): TodoItem {
        return todos[id]
    }
}