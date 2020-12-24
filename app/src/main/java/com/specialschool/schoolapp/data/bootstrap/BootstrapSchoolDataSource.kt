package com.specialschool.schoolapp.data.bootstrap

import com.specialschool.schoolapp.BuildConfig
import com.specialschool.schoolapp.data.SchoolDataSource
import com.specialschool.schoolapp.data.json.SchoolDataJsonParser
import com.specialschool.schoolapp.model.SchoolData

/**
 * 로컬에 저장된 학교 데이터를 불러오는 data source
 */
object BootstrapSchoolDataSource : SchoolDataSource {
    override fun getRemoteSchoolData(): SchoolData? {
        throw Exception("Bootstrap data source doesn't have remote data")
    }

    /**
     * 저장된 json 데이터를 파싱하고 불러온다.
     */
    override fun getSchoolData(): SchoolData? {
        return loadAndParseBootstrapData()
    }

    fun loadAndParseBootstrapData(): SchoolData {
        val stream = this.javaClass.classLoader!!
            .getResource(BuildConfig.BOOTSTRAP_SCHOOL_DATA_FILENAME).openStream()

        return SchoolDataJsonParser.parseJsonData(stream)
    }
}
