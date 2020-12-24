package com.specialschool.schoolapp.domain.schooldata

import com.specialschool.schoolapp.data.userevent.DefaultSchoolAndUserItemRepository
import com.specialschool.schoolapp.di.DefaultDispatcher
import com.specialschool.schoolapp.domain.FlowUseCase
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Firestore에서 사용자 데이터 들을 불러오는 use case
 *
 * TODO: IO Dispatcher? Default dispatcher?
 */
@ExperimentalCoroutinesApi
class LoadUserItemUseCase @Inject constructor(
    private val repository: DefaultSchoolAndUserItemRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Pair<String?, String>, UserItem>(dispatcher) {

    override fun execute(parameters: Pair<String?, String>): Flow<Result<UserItem>> {
        val (userId, itemId) = parameters
        return repository.getObservableUserItem(userId, itemId).map {
            if (it is Result.Success) {
                Result.Success(it.data)
            } else {
                it
            }
        }
    }
}
