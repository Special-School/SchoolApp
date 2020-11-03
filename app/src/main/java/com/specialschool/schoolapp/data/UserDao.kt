package com.specialschool.schoolapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

typealias User = UserEntity

// For room test
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg users: User)
}
