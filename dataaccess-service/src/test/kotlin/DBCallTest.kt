package com.github.vitormbgoncalves.dataaccess.service

import com.github.vitormbgoncalves.repository.TodoListRepositorySQL
import com.github.vitormbgoncalves.todolist.shared.Importance
import com.github.vitormbgoncalves.todolist.shared.TodoItem
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

/**
 * DB Calling testing file
 *
 * @author Vitor Goncalves
 * @since 04.05.2021, ter, 16:58
 */

object DBCallTest : Spek({

    describe("Call TodosItem in data base") {

        val DBCall by memoized { TodoServiceDBCall(TodoListRepositorySQL()) }

        it("should be OK to get all TodoItem") {
            runBlocking {

                DBCall.getAll() shouldContain todo
            }
        }

        it("should be OK to get one TodoItem") {

            DBCall.getTodo(1) shouldBeEqualTo (todo)
        }

        it("should be OK to create a TodoItem") {

            DBCall.create(todo) `should be` (true)
        }

        it("should be OK to update a TodoItem") {
            DBCall.update(2, todo) `should be` (true)
        }

        it("should be OK to delete a TodoItem") {
            DBCall.delete(1) `should be` (true)
        }

        it("should be OK if no delete a TodoItem") {
            DBCall.delete(5) `should be` (false)
        }

        it("should be OK if userdata is value") {
            DBCall.loadUserData("blue") shouldBeEqualTo (UserData("blue"))
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