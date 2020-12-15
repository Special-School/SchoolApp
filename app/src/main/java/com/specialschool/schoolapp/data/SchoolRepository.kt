package com.specialschool.schoolapp.data

import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.data.db.SchoolFtsEntity
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.model.SchoolData
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SchoolRepository @Inject constructor(
    @Named("remoteSchoolDataSource") private val remoteDataSource: SchoolDataSource,
    @Named("bootstrapSchoolDataSource") private val bootstrapDataSource: SchoolDataSource,
    private val database: AppDatabase
) {

    private var dataCache: SchoolData? = null

    val currentSchoolDataVersion: Int
        get() = dataCache?.version ?: 0

    var latestException: Exception? = null
        private set

    // TODO: check last data update

    // 동기화를 위한 lock object
    private val loadDataLock = Any()

    fun refreshCacheWithRemoteSchoolData() {
        if (dataCache != null) {
            return
        }

        val schoolData = try {
            remoteDataSource.getRemoteSchoolData()
        } catch (e: IOException) {
            latestException = e
            throw e
        }
        if (schoolData == null) {
            val e = Exception("Remote returned no data")
            latestException = e
            throw e
        }

        synchronized(loadDataLock) {
            dataCache = schoolData
            populateSearchData(schoolData)
        }

        latestException = null
    }

    fun getSchoolList(): List<School> {
        return getOfflineSchoolData().schools
    }

    private fun getOfflineSchoolData(): SchoolData {
        synchronized(loadDataLock) {
            val offlineData = dataCache ?: getCacheOrBootstrapDataAndPopulateSearch()
            dataCache = offlineData
            return offlineData
        }
    }

    private fun getCacheOrBootstrapDataAndPopulateSearch(): SchoolData {
        val schoolData = getCacheOrBootstrapData()
        populateSearchData(schoolData)
        return schoolData
    }

    private fun getCacheOrBootstrapData(): SchoolData {
        var schoolData = remoteDataSource.getSchoolData()

        if (schoolData != null) {
            return schoolData
        }

        schoolData = bootstrapDataSource.getSchoolData()!!
        return schoolData
    }

    private fun populateSearchData(data: SchoolData) {
        val schoolFtsEntities = data.schools.map { school ->
            SchoolFtsEntity(
                schoolId = school.id,
                schoolName = school.name,
                displayName = insertBlankInString(school.displayName, school.displayName.length - 2),
                fullName = school.displayName
            )
        }
        database.schoolFtsDao().insertAll(schoolFtsEntities)
    }

    private fun insertBlankInString(str: String, index: Int): String {
        val temp = str.toMutableList()
        temp.add(index, ' ')
        return temp.joinToString("")
    }

    fun getSchool(schoolId: String): School {
        return getOfflineSchoolData().schools.firstOrNull { school ->
            school.id == schoolId
        } ?: throw SchoolNotFoundException()
    }
}

class SchoolNotFoundException : Throwable()
