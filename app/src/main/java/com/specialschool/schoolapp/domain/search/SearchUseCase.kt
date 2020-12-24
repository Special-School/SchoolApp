package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.userevent.SchoolAndUserItemRepository
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.FlowUseCase
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.Result.Success
import com.specialschool.schoolapp.util.Result.Loading
import com.specialschool.schoolapp.util.Result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 검색 기능을 비동기로 수행하는 UseCase
 *
 * @property repository 학교 데이터 repo
 * @property queryMatchStrategy 쿼리 매칭 strategy, [FtsQueryMatchStrategy]
 * @property dispatcher IO dispatcher
 */
class SearchUseCase @Inject constructor(
    private val repository: SchoolAndUserItemRepository,
    private val queryMatchStrategy: QueryMatchStrategy,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<SearchParameter, List<UserItem>>(dispatcher) {

    override fun execute(parameters: SearchParameter): Flow<Result<List<UserItem>>> {
        val (userId, query) = parameters
        return repository.getObservableUserItems(userId).map { result ->
            when (result) {
                is Success -> {
                    val searchResults = queryMatchStrategy.searchSchools(
                        result.data.userItems, query
                    )
                    Success(searchResults)
                }
                is Loading -> result
                is Error -> result
            }
        }
    }
}

data class SearchParameter(
    val userId: String?,
    val query: String
)
