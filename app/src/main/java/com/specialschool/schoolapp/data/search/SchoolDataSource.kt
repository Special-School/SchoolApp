package com.specialschool.schoolapp.data.search

import com.specialschool.schoolapp.model.School

object FakeSchoolDataSource : SchoolDataSource {
    override fun getSchoolData(): List<School> {
        return listOf(
            School("school-1", "school one"),
            School("school-2", "school two"),
            School("school-3", "special school")
        )
    }
}

interface SchoolDataSource {
    fun getSchoolData(): List<School>
}
