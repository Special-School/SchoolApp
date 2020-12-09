package com.specialschool.schoolapp.data

import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.model.School
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SchoolRepository @Inject constructor(
    @Named("remoteSchoolDataSource") private val dataSource: SchoolDataSource,
    private val database: AppDatabase
) {

    private var dataCache: List<School>? = null

    /* TODO: check last data update,
         current data version */

    // 동기화를 위한 lock object
    private val loadDataLock = Any()

    fun refreshCacheWithRemoteSchoolData() {
        TODO("Not yet implemented")
    }

    fun getOfflineSchoolData(): List<School> {
        TODO("Not yet implemented")
    }

    private fun getCacheDataAndPopulateSearch(): List<School> {
        TODO("Not yet implemented")
    }

    private fun getCacheData(): List<School> {
        TODO("Not yet implemented")
    }

    fun populateSearchData(data: List<School>) {
        TODO("Not yet implemented")
    }

    // TODO: 임시 구현
    fun getSchools(): List<School>? {
        return dataSource.getSchoolData()
    }
}
