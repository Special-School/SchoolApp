package com.specialschool.schoolapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<SchoolEntity>)

    @Query("SELECT schoolName FROM school WHERE schoolName LIKE :query")
    fun searchAllSchools(query: String): List<String>
}
