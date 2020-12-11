package com.specialschool.schoolapp.data

import com.specialschool.schoolapp.model.SchoolData

interface SchoolDataSource {
    fun getRemoteSchoolData(): SchoolData?
    fun getSchoolData(): SchoolData?
}
