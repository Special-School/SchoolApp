package com.specialschool.schoolapp.test.util

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.data.db.SchoolFtsDao
import com.nhaarman.mockitokotlin2.mock
import com.specialschool.schoolapp.data.db.SchoolFtsEntity
import org.mockito.Mockito

class MockAppDatabase : AppDatabase() {
    override fun schoolFtsDao(): SchoolFtsDao {
        return Mockito.mock(SchoolFtsDao::class.java)
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        return Mockito.mock(SupportSQLiteOpenHelper::class.java)
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return Mockito.mock(InvalidationTracker::class.java)
    }

    override fun clearAllTables() {}
}

class MockSearchAppDatabase : AppDatabase() {
    override fun schoolFtsDao(): SchoolFtsDao {
        return object : SchoolFtsDao {
            override fun insertAll(schools: List<SchoolFtsEntity>) {
                TODO("Not implemented")
            }

            override fun searchBySchoolName(query: String): List<String> {
                TODO("when (query) { TestData에서 불러온다 }")
            }

            override fun searchByDisplayName(query: String): List<String> {
                TODO("Not implemented")
            }

            override fun searchByFullName(query: String): List<String> {
                TODO("Not yet implemented")
            }
        }
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return mock {}
    }

    override fun clearAllTables() {
        TODO("Not implemented")
    }
}
