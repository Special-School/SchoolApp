package com.specialschool.schoolapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SchoolFtsEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun schoolFtsDao(): SchoolFtsDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "schoolapp.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
