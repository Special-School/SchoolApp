package com.specialschool.schoolapp.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "school", indices = [Index(value = ["schoolName"])])
data class SchoolEntity(
    val schoolId: String,
    @PrimaryKey val schoolName: String
)
