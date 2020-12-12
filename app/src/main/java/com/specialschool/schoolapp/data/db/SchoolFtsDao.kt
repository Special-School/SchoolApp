package com.specialschool.schoolapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchoolFtsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<SchoolFtsEntity>)

    @Query("SELECT schoolName FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchBySchoolName(query: String): List<String>

    @Query("SELECT displayName FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchByDisplayName(query: String): List<String>
}
