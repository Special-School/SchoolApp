package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.search.SchoolRepository
import com.specialschool.schoolapp.di.IoDispatcher
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
 *
 * TODO: Use case를 테스트 가능하게 변경
 */
class SearchUseCase @Inject constructor(
    private val repository: SchoolRepository,
    private val queryMatchStrategy: QueryMatchStrategy,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    /**
     * 검색 use case를 비동기로 실행하고 결과를 [Result]로 반환한다.
     *
     * @param parameters query
     */
    suspend operator fun invoke(parameters: String): Result<List<School>> {
        return try {
            withContext(dispatcher) {
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * 비동기로 실행할 코드 부분
     */
    fun execute(parameters: String): List<School> {
        val query = parameters
        val schools = repository.getSchools()
        return queryMatchStrategy.searchSchools(schools, query)
    }
}
