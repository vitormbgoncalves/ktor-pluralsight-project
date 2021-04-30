package com.github.vitormbgoncalves

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.todolist.restapi.moduleWithDependencies
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotBeNull
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

/**
 * RestAPI testing file
 *
 * @author Vitor Goncalves
 * @since 23.04.2021, sex, 11:36
 */

object GetTodosSpec : Spek({

    /*val todo = TodoItem(
        1,
        "Add database processing",
        "Add backend support to this code",
        "Vitor",
        LocalDate.of(2021, 4, 21),
        Importance.HIGH
    )

    describe("Get the Todos") {
        val engine = TestApplicationEngine()
        engine.start(wait = false)

        with(engine) {
            (environment.config as MapApplicationConfig).apply {
                put("ktor.environment", "test")
            }
        }

        val mockTodoService = mockk<TodoService>()

        beforeEachTest {
            clearMocks(mockTodoService)
        }

        engine.application.moduleWithDependencies(mockTodoService)

        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        with(engine) {
            it("should be OK to get the list of todos") {

                every { mockTodoService.getAll() } returns listOf(todo)

                handleRequest(HttpMethod.Get, "/api/todos").apply {
                    response.status().`should be`(HttpStatusCode.OK)
                }
            }

            it("should get the todos") {

                every { mockTodoService.getAll() } returns listOf(todo)

                with(handleRequest(HttpMethod.Get, "/api/todos")) {
                    response.content
                        .shouldNotBeNull()
                        .shouldContain("database")
                }
            }

            it("should create a todo") {

                every { mockTodoService.create(any()) } returns true

                with(
                    handleRequest(HttpMethod.Post, "/api/todos") {
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        setBody(mapper.writeValueAsString(todo))
                    }
                ) {
                    response.status().`should be`(HttpStatusCode.Created)
                }
            }

            it("should update the todos") {

                every { mockTodoService.update(any(), any()) } returns true

                with(
                    handleRequest(HttpMethod.Put, "api/todos/1") {
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        setBody(mapper.writeValueAsString(todo))
                    }
                ) {
                    response.status().`should be`(HttpStatusCode.NoContent)
                }
            }

            it("should delete the todos") {

                every { mockTodoService.delete(any()) } returns true

                with(handleRequest(HttpMethod.Delete, "/api/todos/1")) {
                    response.status().`should be`(HttpStatusCode.NoContent)
                }
            }

            it("should get the todo if id is set") {

                every { mockTodoService.getTodo(1) } returns todo

                with(handleRequest(HttpMethod.Get, "/api/todos/1")) {
                    response.content
                        .shouldNotBeNull()
                        .shouldContain("database")
                }
            }

            it("should return an error if the id is invalid") {

                every { mockTodoService.getTodo(1) } throws Exception()

                with(handleRequest(HttpMethod.Get, "/api/todos/2")) {
                    response.status().shouldBeEqualTo(HttpStatusCode.NotFound)
                }
            }
        }
    }*/
})