package com.specialschool.schoolapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "schoolfts")
@Fts4
data class SchoolFtsEntity(

    @ColumnInfo(name = "schoolId")
    val schoolId: String,

    @ColumnInfo(name = "schoolName")
    val schoolName: String,

    @ColumnInfo(name = "displayName")
    val displayName: String
)
