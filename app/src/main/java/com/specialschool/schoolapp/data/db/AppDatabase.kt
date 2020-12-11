package com.specialschool.schoolapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room 데이터베이스 클래스
 *
 * **See Also:** [@Database](https://developer.android.com/reference/androidx/room/Database)
 */
@Database(entities = [SchoolFtsEntity::class, SchoolEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun schoolFtsDao(): SchoolFtsDao

    abstract fun schoolDao(): SchoolDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "schoolapp.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
