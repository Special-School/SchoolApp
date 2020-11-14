package com.specialschool.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// For room test
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
)
