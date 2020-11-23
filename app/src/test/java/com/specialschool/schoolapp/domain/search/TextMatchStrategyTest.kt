package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.test.data.MainCoroutineRule
import com.specialschool.schoolapp.test.data.TestData
import com.specialschool.schoolapp.test.data.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Rule

@ExperimentalCoroutinesApi
class TextMatchStrategyTest(private val strategy: TextMatchStrategy) {

    companion object {
        val coroutineRule = MainCoroutineRule()
    }

    @get:Rule
    var coroutineRule = Companion.coroutineRule

    private fun assertSearchResults(
        query: String,
        expectedSchools: List<School>
    ) = coroutineRule.runBlockingTest {
        val schools = strategy.searchSchools(TestData.schoolList, query)
        assertThat(schools, `is`(equalTo(expectedSchools)))
    }
}
