package com.specialschool.schoolapp.data

import com.specialschool.schoolapp.model.SchoolData

/**
 * School data source 인터페이스
 */
interface SchoolDataSource {
    fun getRemoteSchoolData(): SchoolData?
    fun getSchoolData(): SchoolData?
}
