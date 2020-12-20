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

/**
 * Domain layer에서 학교 데이터에 접근하기 위한 repository
 *
 * @property remoteDataSource 네트워크로 불러오는 data source
 * @property bootstrapDataSource 로컬에 저장된 data source
 * @property database Room database
 */
@Singleton
class SchoolRepository @Inject constructor(
    @Named("remoteSchoolDataSource") private val remoteDataSource: SchoolDataSource,
    @Named("bootstrapSchoolDataSource") private val bootstrapDataSource: SchoolDataSource,
    private val database: AppDatabase
) {

    // In memory cache of the school data
    private var dataCache: SchoolData? = null

    val currentSchoolDataVersion: Int
        get() = dataCache?.version ?: 0

    var latestException: Exception? = null
        private set

    // TODO: check last data update

    // 동기화를 위한 lock object
    private val loadDataLock = Any()

    /**
     * 네트워크를 통해 [dataCache]를 업데이트한다.
     */
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

        // 데이터를 성공적으로 불러오면 캐시를 업데이트한다.
        synchronized(loadDataLock) {
            dataCache = schoolData
            populateSearchData(schoolData)
        }

        latestException = null
    }

    /**
     * [School] 리스트를 반환한다.
     */
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
        // 1. Http 로컬 캐시를 사용해 불러온다.
        var schoolData = remoteDataSource.getSchoolData()

        if (schoolData != null) {
            return schoolData
        }

        // 2. Http 연결을 통해 불러온다.
        schoolData = try {
            remoteDataSource.getRemoteSchoolData()
        } catch (e: IOException) {
            latestException = e
            null
        }

        if (schoolData != null) {
            return schoolData
        }

        // 3. 위의 경우가 모두 실패할 때, 내장된 파일로 부터 데이터를 불러온다.
        schoolData = bootstrapDataSource.getSchoolData()!!
        return schoolData
    }

    // Full text search를 위해 불러온 데이터를 local db에 저장한다.
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

    // TODO: Need refactoring
    private fun insertBlankInString(str: String, index: Int): String {
        val temp = str.toMutableList()
        temp.add(index, ' ')
        return temp.joinToString("")
    }

    /**
     * 특정 학교 데이터를 불러온다.
     */
    fun getSchool(schoolId: String): School {
        return getOfflineSchoolData().schools.firstOrNull { school ->
            school.id == schoolId
        } ?: throw SchoolNotFoundException()
    }
}

class SchoolNotFoundException : Throwable()
