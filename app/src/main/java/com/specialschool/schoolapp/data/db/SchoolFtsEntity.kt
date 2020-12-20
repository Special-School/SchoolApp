package com.specialschool.schoolapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

/**
 * [School] 데이터를 FTS 검색하기 위한 entity
 */
@Entity(tableName = "schoolfts")
@Fts4
data class SchoolFtsEntity(

    @ColumnInfo(name = "schoolId")
    val schoolId: String,

    @ColumnInfo(name = "schoolName")
    val schoolName: String,

    @ColumnInfo(name = "displayName")
    val displayName: String,

    @ColumnInfo(name = "fullName")
    val fullName: String,
)
