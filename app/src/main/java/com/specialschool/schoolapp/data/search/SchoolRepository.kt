package com.specialschool.schoolapp.data.search

import com.specialschool.schoolapp.model.School
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSchoolRepository @Inject constructor(
    private val dataSource: SchoolDataSource
) : SchoolRepository {
    override fun getSchools(): Flow<Result<School>> {
        TODO("Not yet implemented")
    }
}

interface SchoolRepository {

    fun getSchools(): Flow<Result<School>>
}
