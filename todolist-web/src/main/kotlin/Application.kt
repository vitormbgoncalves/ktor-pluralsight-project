package com.github.vitormbgoncalves.todolist.web

import com.github.mustachejava.DefaultMustacheFactory
import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.dataaccess.service.TodoServiceAPICALL
import com.github.vitormbgoncalves.todolist.oauth.client.IOAuthClient
import com.github.vitormbgoncalves.todolist.oauth.client.OAuthClient
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.mustache.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.sessions.*
import keycloakOAuth
import keycloakProvider
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

/**
 * Main application class
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 11:48
 */

fun main(args: Array<String>) {
    embeddedServer(CIO, commandLineEnvironment(args)).start(true)
}

@Suppress("unused")
fun Application.module() {
    install(Koin) {
        modules(todoAppModule2)
    }

    val oauthHttpClient: HttpClient = HttpClient(io.ktor.client.engine.cio.CIO).apply {
        environment.monitor.subscribe(ApplicationStopping) {
            close()
        }
    }

    val todoService by inject<TodoService>()

    moduleWithDependencies(todoService, oauthHttpClient)
}

fun Application.moduleWithDependencies(todoService: TodoService, oauthHttpClient: HttpClient) {
    install(StatusPages) {
        when {
            isDev -> {
                this.exception<Throwable> { e ->
                    call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
                    throw e
                }
            }
            isTest -> {
                this.exception<Throwable> { e ->
                    call.response.status(HttpStatusCode.InternalServerError)
                }
            }
            isProd -> {
                this.exception<Throwable> { e ->
                    call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
                    throw e
                }
            }
        }
    }

    install(Authentication) {
        oauth(keycloakOAuth) {
            skipWhen { call -> call.sessions.get<UserSession>() != null }
            providerLookup = { keycloakProvider }
            client = oauthHttpClient
            urlProvider = {
                redirectUrl("/todos")
            }
        }
    }

    install(DefaultHeaders)

    install(CallLogging)

    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    install(Sessions) {
        cookie<UserSession>("KTOR_SESSION", storage = SessionStorageMemory())
    }

    install(Routing) {
        if (isDev) trace {
            application.log.trace(it.buildText())
        }
        launch {
            todos(todoService)
        }
        staticResources()
    }
}

data class UserSession(val id: String, val name: String, val backgroundColor: String)

val Application.environmentKind get() = environment.config.property("ktor.environment").getString()
val Application.isDev get() = environmentKind == "dev"
val Application.isTest get() = environmentKind == "test"
val Application.isProd get() = environmentKind != "dev" && environmentKind != "test"

val todoAppModule2 = module {
    single<TodoService> { TodoServiceAPICALL(get()) }
    single<IOAuthClient> { OAuthClient() }
}

private fun ApplicationCall.redirectUrl(path: String): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val hostPort = request.host() + request.port().let { port -> if (port == defaultPort) "" else ":$port" }
    val protocol = "http"
    return "$protocol://$hostPort$path"
}