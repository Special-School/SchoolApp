package com.specialschool.schoolapp.model

import java.time.LocalDate

data class School(
    val id: String,
    val province: String,
    val type: SchoolType,
    val name: String,
    val category: Disability,
    val principalName: String,
    val authDate: LocalDate,
    val openDate: LocalDate,
    val contact: Contact,
    val faxNumber: String,
    val zipCode: Int,
    val address: String,
    val website: String,
    val coordinate: Coordinate
)

enum class SchoolType(val displayName: String) {
    NATIONAL("국립"),
    PUBLIC("공립"),
    PRIVATE("사립"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(type: String): SchoolType {
            return when (type) {
                "국립" -> NATIONAL
                "공립" -> PUBLIC
                "사립" -> PRIVATE
                else -> UNKNOWN
            }
        }
    }
}

enum class Disability(val displayName: String) {
    VISUAL("시각장애"),
    HEARING("청각장애"),
    INTELLECTUAL("지적장애"),
    PHYSICAL("지체장애"),
    EMOTIONAL("정서장애"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(type: String): Disability {
            return when (type) {
                "시각장애" -> VISUAL
                "청각장애" -> HEARING
                "지적장애" -> INTELLECTUAL
                "지체장애" -> PHYSICAL
                "정서장애" -> EMOTIONAL
                else -> UNKNOWN
            }
        }
    }
}

data class Contact(
    val principalNumber: String,
    val adminNumber: String,
    val staffRoomNumber: String
)
