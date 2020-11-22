package com.specialschool.schoolapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<SchoolFtsEntity>)

    @Query("SELECT schoolId FROM schoolfts WHERE schoolfts MATCH :query")
    fun searchAllSchools(query: String): List<String>
}
