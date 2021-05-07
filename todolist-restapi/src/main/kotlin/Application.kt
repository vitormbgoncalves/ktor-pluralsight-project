package com.github.vitormbgoncalves.todolist.restapi

import com.auth0.jwk.JwkProviderBuilder
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.vitormbgoncalves.dataaccess.service.TodoService
import com.github.vitormbgoncalves.dataaccess.service.TodoServiceDBCall
import com.github.vitormbgoncalves.repository.TodoListRepository
import com.github.vitormbgoncalves.repository.TodoListRepositorySQL
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.net.URL
import java.util.concurrent.*

/**
 * Main application class
 *
 * @author Vitor Goncalves
 * @since 21.04.2021, qua, 16:14
 */

fun main(args: Array<String>) {
    embeddedServer(CIO, commandLineEnvironment(args)).start(true)
}

/*val TodoContentV1 = ContentType("com/github/vitormbgoncalves/restApi/application", "vnd.todoapi.v1+json")*/

@Suppress("unused") // Referenced in com.github.vitormbgoncalves.restApi.application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Koin) {
        modules(todoAppModule)
    }

    val todoService by inject<TodoService>()
    moduleWithDependencies(todoService)
}

fun Application.moduleWithDependencies(todoService: TodoService) {

    val jwkIssuer = environment.config.property("jwt.jwkIssuer").getString()
    val jwksUrl = URL(environment.config.property("jwt.jwksURL").getString())
    val jwkRealm = environment.config.property("jwt.jwkRealm").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val jwkProvider = JwkProviderBuilder(jwksUrl)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("jwt") {
            verifier(jwkProvider, jwkIssuer)
            realm = jwkRealm
            var cred: JWTCredential
            var principal: JWTPrincipal
            validate { credentials ->
                cred = credentials
                log.debug("Credentials audience: ${credentials.payload.audience}")
                log.debug("audience: $audience")

                if (credentials.payload.audience.contains(audience)) {
                    principal = JWTPrincipal(credentials.payload)
                    principal
                } else {
                    null
                }
            }
        }
    }

    install(Routing) {
        trace { application.log.trace(it.buildText()) }
        todoApi(todoService)
    }

    install(CallLogging)

    install(StatusPages) {
        this.exception<Throwable> { e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain)
            throw e
        }
    }

    install(ContentNegotiation) {

        // register(TodoContentV1, JacksonConverter)

        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
        /*jackson(TodoContentV1) {
            enable(SerializationFeature.INDENT_OUTPUT)
        }*/
    }
}

val todoAppModule = module {
    single<TodoService> { TodoServiceDBCall(get()) }
    single<TodoListRepository> { TodoListRepositorySQL() }
}