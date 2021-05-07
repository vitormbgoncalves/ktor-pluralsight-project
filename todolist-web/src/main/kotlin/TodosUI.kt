package com.github.vitormbgoncalves.todolist.web

import com.auth0.jwt.JWT
import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.dataaccess.service.UserData
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import com.github.vitormbgoncalves.todolist.web.todolist.web.TodoVM
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.mustache.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import java.io.File
import java.time.LocalDate
import keycloakOAuth
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Html routing file
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 22:02
 */


suspend fun Routing.todos(todoservice: TodoService) {

    authenticate(keycloakOAuth) {
        get("/todos") {

            val userData: UserData

            when(val userSession = call.sessions.get<UserSession>()) {
                null -> {
                    val subject = getSubject()
                    userData = todoservice.loadUserData(subject)
                    call.sessions.set(UserSession(id = subject, name = "John", backgroundColor = userData.background))
                    call.application.environment.log.info("JWT Token is: ${call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()?.accessToken}")
                }
                else -> {
                    userData = UserData(background = "green")
                }
            }

            val todos = todoservice.getAll()
            val todoVm = TodoVM(todos, userData.background)


            call.respond(
                MustacheContent("todos.hbs", mapOf("todos" to todoVm))
            )
        }
    }
}

fun PipelineContext<Unit, ApplicationCall>.getSubject(): String {
    val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
    val jwt = JWT.decode(principal?.accessToken)
    return jwt.subject!!
}