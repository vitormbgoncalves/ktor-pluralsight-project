package com.github.vitormbgoncalves.todolist.restapi

import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * Data access routing function
 *
 * @author Vitor Goncalves
 * @since 20.04.2021, ter, 11:00
 */

fun Routing.todoApi(todoService: TodoService) {
    authenticate("jwt") {
        route("/api") {

            /*// content type header versioning
            header("Accept", "com.github.vitormbgoncalves.restApi.application/vnd.todoapi.v1+json") {
                get("/todos") {
                    call.respond(todo3)
                }
            }

            accept(TodoContentV1) {
                get("/todos") {
                    call.respond(todo3)
                }
            }*/

            get("/todos") {
                val todos = todoService.getAll()
                call.respond(todos)
            }
            get("/todos/{id}") {
                val id = call.parameters["id"] ?: return@get
                try {
                    val todos = todoService.getTodo(id.toInt())
                    call.respond(todos)
                } catch (e: Throwable) {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            post("/todos") {
                val todo = call.receive<TodoItem>()
                todoService.create(todo)
                call.respond(HttpStatusCode.Created)
            }
            put("/todos/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Missing id")
                val todo = call.receive<TodoItem>()
                todoService.update(id.toInt(), todo)
                call.respond(HttpStatusCode.NoContent)
            }
            delete("/todos/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Missing id")
                todoService.delete(id.toInt())
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}