package com.github.vitormbgoncalves.todolist.web

import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.routing.*
import java.io.File
import keycloakOAuth

/**
 * Routing static resources file
 *
 * @author Vitor Goncalves
 * @since 22.04.2021, qui, 19:43
 */

fun Routing.staticResources() {
    static {
        staticRootFolder = File("todolist-web/assets")
        static("css") {
            files("css")
        }
        static("js") {
            files("js")
        }
        authenticate(keycloakOAuth) {
            default("index.html")
        }
    }
}