package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.model.School
import javax.inject.Inject

interface QueryMatchStrategy {
    suspend fun searchSchools(schools: List<School>, query: String): List<School>
}

class FtsQueryMatchStrategy @Inject constructor(
    private val appDatabase: AppDatabase
) : QueryMatchStrategy {

    override suspend fun searchSchools(schools: List<School>, query: String): List<School> {
        if (query.isEmpty()) {
            return schools
        }
        val schoolIds = appDatabase.schoolFtsDao().searchAllSchools(query.toLowerCase()).toSet()
        return schools.filter { it.id in schoolIds }
    }
}
