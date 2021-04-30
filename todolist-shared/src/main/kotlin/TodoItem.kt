package com.github.vitormbgoncalves.todolist.shared

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate

/**
 * Data Class TodoItems
 *
 * @author Vitor Goncalves
 * @since 20.04.2021, ter, 11:08
 */

data class TodoItem(
    val id: Int,
    val title: String,
    val detail: String,
    val assignedTo: String,
    @JsonSerialize(using = ToStringSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val dueDate: LocalDate,
    val importance: Importance
)

enum class Importance {
    LOW, MEDIUM, HIGH
}