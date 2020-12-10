package com.specialschool.schoolapp.data.json

data class SchoolTemp(
    val region: String,
    val type: String,
    val name: String,
    val category: String,
    val principalName: String,
    val authDate: String,
    val openDate: String,
    val principalNumber: String,
    val adminNumber: String,
    val staffRoomNumber: String,
    val faxNumber: String,
    val zipCode: Int,
    val address: String,
    val website: String,
    val latitude: Long,
    val longitude: Long
)
