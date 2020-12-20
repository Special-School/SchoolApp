package com.specialschool.schoolapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The data access object for [SchoolFtsEntity]
 */
@Dao
interface SchoolFtsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<SchoolFtsEntity>)

    @Query("SELECT schoolName FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchBySchoolName(query: String): List<String>

    @Query("SELECT displayName FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchByDisplayName(query: String): List<String>

    @Query("SELECT fullName FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchByFullName(query: String): List<String>
}
