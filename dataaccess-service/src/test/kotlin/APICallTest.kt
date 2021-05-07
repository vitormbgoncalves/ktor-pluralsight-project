package com.github.vitormbgoncalves.dataaccess.service

import com.github.vitormbgoncalves.todolist.oauth.client.OAuthClient
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

/**
 * API Calling testing file
 *
 * @author Vitor Goncalves
 * @since 04.05.2021, ter, 20:40
 */

object APICallTest : Spek({

    describe("Call Todos in Rest API\"") {

        val OauthClient = mockk<OAuthClient>(relaxed = true)

        val APICall = TodoServiceAPICALL(OauthClient)

        beforeEachTest {
            clearMocks(OauthClient)
        }

        it("should be OK to get all TodoItem") {
            runBlocking {

                coEvery {
                    OauthClient.callApi(
                        "http://localhost:8081/api/todos",
                        "",
                        ""
                    )
                } returns items

                APICall.getAll() shouldContain todo
            }
        }
    }
})

private val todo: TodoItem = TodoItem(
    1,
    "Add database processing 1",
    "Add backend support to this code",
    "Vitor",
    LocalDate.of(2021, 5, 3),
    Importance.HIGH
)

private const val items = "[ {\n" +
    "  \"id\" : 1,\n" +
    "  \"title\" : \"Add database processing 1\",\n" +
    "  \"detail\" : \"Add backend support to this code\",\n" +
    "  \"assignedTo\" : \"Vitor\",\n" +
    "  \"dueDate\" : \"2021-05-03\",\n" +
    "  \"importance\" : \"HIGH\"\n" +
    "}]"