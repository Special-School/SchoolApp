package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.model.UserItem
import javax.inject.Inject

interface QueryMatchStrategy {
    fun searchSchools(userItems: List<UserItem>, query: String): List<UserItem>
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
     * @param userItems 검색할 학교 데이터 리스트
     * @param query 검색 쿼리
     */
    override fun searchSchools(userItems: List<UserItem>, query: String): List<UserItem> {
        if (query.isEmpty()) {
            return userItems
        }
        var schoolNames = appDatabase.schoolFtsDao().searchBySchoolName("$query*").toSet()
        if (schoolNames.isEmpty()) {
            schoolNames = appDatabase.schoolFtsDao().searchByDisplayName("$query*").toSet()
        }
        if (schoolNames.isEmpty()) {
            schoolNames = appDatabase.schoolFtsDao().searchByFullName("$query*").toSet()
        }
        return userItems.filter { it.school.name in schoolNames }
    }
}
