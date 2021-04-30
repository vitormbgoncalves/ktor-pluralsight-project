package com.github.vitormbgoncalves.dataaccess.service

import com.beust.klaxon.Parser
import com.github.vitormbgoncalves.todolist.oauth.client.IOAuthClient
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*

/**
 * Data access methods interface implementation Api Call
 *
 * @author Vitor Goncalves
 * @since 30.04.2021, sex, 10:51
 */

class TodoServiceAPICALL(val oauthClient: IOAuthClient) : TodoService {

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    val apiEndpoint = "http://localhost:8081/api/todos"

    override suspend fun getAll(): List<TodoItem> {
        val token = oauthClient.getClientCredential(
            "http://localhost:8180/auth/realms/Ktor/protocol/openid-connect/token",
            "KtorApp",
            "fe028175-f68b-4fe3-b572-4a24e1f0ad63",
            "ktoruser",
            "minhasenhasecreta",
            listOf("openid")
        )

        val res = token?.let {
            oauthClient.callApi(apiEndpoint, it.tokenType, it.token)
        }
        val items = res?.let {
            val parser = Parser.default()
            parser.parse(StringBuilder(it)) as List<TodoItem>
        }

        return if (items != null) {
            items
        } else {
            listOf()
        }
        println("Token is: $token")
    }

    override fun getTodo(id: Int): TodoItem {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun create(todo: TodoItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, todo: TodoItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun loadUserData(userId: String): UserData {
        return com.github.vitormbgoncalves.dataaccess.service.UserData("blue")
    }
}

data class UserData(val backgraound: String)