package com.specialschool.schoolapp.data.search

import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSchoolRepository @Inject constructor(
    private val dataSource: SchoolDataSource
) : SchoolRepository {
    override fun getSchools(): List<School> {
        return dataSource.getSchoolData()
    }
}

interface SchoolRepository {

    fun getSchools(): List<School>
}
