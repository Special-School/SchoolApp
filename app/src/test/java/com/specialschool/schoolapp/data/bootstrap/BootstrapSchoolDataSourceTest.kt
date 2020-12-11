package com.specialschool.schoolapp.data.bootstrap

import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.hasSize

class BootstrapSchoolDataSourceTest {

    @Test
    fun `Bootstrap 파일로 부터 정상적으로 json 데이터 불러온다`() {
        val data = BootstrapSchoolDataSource.loadAndParseBootstrapData()
        assertThat(data.schools, hasSize(greaterThan(0)))
    }
}
