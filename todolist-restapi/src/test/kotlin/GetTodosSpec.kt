package com.github.vitormbgoncalves.restapi

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.todolist.restapi.moduleWithDependencies
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import com.google.common.io.Resources.getResource
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldNotBeNull
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.Instant
import java.time.LocalDate

/**
 * Full testing Rest Api routes, using Spek 2 framework,
 * Ktor Test Engine, MockK, Kluent and JWK authentication provider mock
 *
 * @author Vitor Goncalves
 * @since 23.04.2021, sex, 11:36
 */

object GetTodosSpec : Spek({

    /*
    Istance of TodoItem for mocking
     */
    val todo = TodoItem(
        1,
        "Add database processing",
        "Add backend support to this code",
        "Vitor",
        LocalDate.of(2021, 4, 21),
        Importance.HIGH
    )

    /*
    JWK authentication provider mock
     */
    fun jwtAuthMock(): String {

        val privateKey = getResource("certs/jwt-private-key.pem").readText(Charsets.UTF_8)

        val secret = """(-----BEGIN PRIVATE KEY-----|-----END PRIVATE KEY-----)"""
            .toRegex().replace(privateKey, "")
            .trim()

        val header = JWTAuthHeader(
            "RS256",
            "JWT", "OZdxpBsB4bmypwduD8tbM8L9JFwo-f8oVzNok2KUBk4"
        )

        val payload = JWTAuthPayload(
            Instant.now().epochSecond + 3600,
            Instant.now().epochSecond,
            "http://localhost:8180/auth/realms/Ktor",
            "account"
        )

        return jwtAuth.createToken(secret, header, payload)
    }

    val jwksURL = getResource("certs/jwks.json").toURI().toString()

    val jwtToken = "Bearer ${jwtAuthMock()}"

    val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    describe("Get the Todos") {

        val engine = TestApplicationEngine()
        engine.start(wait = false)

        with(engine) {
            (environment.config as MapApplicationConfig).apply {
                put("jwt.jwkIssuer", "http://localhost:8180/auth/realms/Ktor")
                put("jwt.jwksURL", jwksURL)
                put("jwt.jwkRealm", "ktor")
                put("jwt.audience", "account")
            }
        }

        val mockTodoService = mockk<TodoService>()

        beforeEachTest {
            clearMocks(mockTodoService)
        }

        engine.application.moduleWithDependencies(mockTodoService)

        with(engine) {

            it("should be OK to get the list of todos") {

                coEvery { mockTodoService.getAll() } returns listOf(todo)

                handleRequest(HttpMethod.Get, "/api/todos") {
                    addHeader("Authorization", jwtToken)
                }.apply {
                    response.status().`should be`(HttpStatusCode.OK)
                }
            }

            it("should be OK to get the todos") {

                coEvery { mockTodoService.getAll() } returns listOf(todo)

                with(
                    handleRequest(HttpMethod.Get, "/api/todos") {
                        addHeader("Authorization", jwtToken)
                    }
                ) {
                    response.content
                        .shouldNotBeNull()
                        .shouldContain("database")
                }
            }

            it("should be OK to create a todo") {

                every { mockTodoService.create(any()) } returns true

                with(
                    handleRequest(HttpMethod.Post, "/api/todos") {
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        addHeader("Authorization", jwtToken)
                        setBody(mapper.writeValueAsString(todo))
                    }
                ) {
                    response.status().`should be`(HttpStatusCode.Created)
                }
            }

            it("should be OK to update the todos") {

                every { mockTodoService.update(any(), any()) } returns true

                with(
                    handleRequest(HttpMethod.Put, "api/todos/1") {
                        addHeader("Authorization", jwtToken)
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        setBody(mapper.writeValueAsString(todo))
                    }
                ) {
                    response.status().`should be`(HttpStatusCode.NoContent)
                }
            }

            it("should be OK to delete the todos") {

                every { mockTodoService.delete(any()) } returns true

                with(
                    handleRequest(HttpMethod.Delete, "/api/todos/1") {
                        addHeader("Authorization", jwtToken)
                    }
                ) {
                    response.status().`should be`(HttpStatusCode.NoContent)
                }
            }

            it("should be OK to get the todo if id is set") {

                every { mockTodoService.getTodo(1) } returns todo

                with(
                    handleRequest(HttpMethod.Get, "/api/todos/1") {
                        addHeader("Authorization", jwtToken)
                    }
                ) {
                    response.content
                        .shouldNotBeNull()
                        .shouldContain("database")
                }
            }

            it("should be OK to return an error if the id is invalid") {

                every { mockTodoService.getTodo(1) } throws Exception()

                with(
                    handleRequest(HttpMethod.Get, "/api/todos/9") {
                        addHeader("Authorization", jwtToken)
                    }
                ) {
                    response.status().shouldBeEqualTo(HttpStatusCode.NotFound)
                }
            }
        }
    }
})