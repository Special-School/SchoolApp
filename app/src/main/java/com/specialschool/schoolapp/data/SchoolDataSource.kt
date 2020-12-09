package com.specialschool.schoolapp.data

import com.specialschool.schoolapp.model.School

object FakeSchoolDataSource : SchoolDataSource {
    override fun getRemoteSchoolDatas(): List<School>? {
        throw Exception("It doesn't have remote data")
    }

    override fun getSchoolData(): List<School>? {
        return listOf(
            School("school-1", "school one"),
            School("school-2", "school two"),
            School("school-3", "special school")
        )
    }
}

interface SchoolDataSource {
    fun getRemoteSchoolDatas(): List<School>?
    fun getSchoolData(): List<School>?
}
