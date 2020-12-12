package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.model.School
import javax.inject.Inject

interface QueryMatchStrategy {
    fun searchSchools(schools: List<School>, query: String): List<School>
}

/**
 * Full text search를 이용해 학교 데이터를 검색한다.
 *
 * @property appDatabase Room DB
 */
class FtsQueryMatchStrategy @Inject constructor(
    private val appDatabase: AppDatabase
) : QueryMatchStrategy {

    /**
     * @return 매치하는 학교 데이터 리스트
     * @param schools 검색할 학교 데이터 리스트
     * @param query 검색 쿼리
     */
    override fun searchSchools(schools: List<School>, query: String): List<School> {
        if (query.isEmpty()) {
            return schools
        }
        var schoolNames = appDatabase.schoolFtsDao().searchBySchoolName("$query*").toSet()
        if (schoolNames.isEmpty()) {
            schoolNames = appDatabase.schoolFtsDao().searchByDisplayName("$query*").toSet()
        }
        return schools.filter { school -> school.name in schoolNames }
    }
}
