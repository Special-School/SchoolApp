package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.SchoolRepository
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.UseCase
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 검색 기능을 비동기로 수행하는 UseCase
 *
 * @property repository 학교 데이터 repo
 * @property queryMatchStrategy 쿼리 매칭 strategy, [FtsQueryMatchStrategy]
 * @property dispatcher IO dispatcher
 */
class SearchUseCase @Inject constructor(
    private val repository: SchoolRepository,
    private val queryMatchStrategy: QueryMatchStrategy,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<String, List<School>>(dispatcher) {

    override fun execute(parameters: String): List<School> {
        val query = parameters
        val schools = repository.getSchools() ?: emptyList()
        return queryMatchStrategy.searchSchools(schools, query)
    }
}
