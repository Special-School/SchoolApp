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
import kotlin.IllegalStateException

@ExperimentalCoroutinesApi
// TODO: IO Dispatcher? Default dispatcher?
class LoadUserItemsUseCase @Inject constructor(
    private val repository: DefaultSchoolAndUserItemRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<String?, List<UserItem>>(dispatcher) {

    override fun execute(parameters: String?): Flow<Result<List<UserItem>>> {
        return repository.getObservableUserItems(parameters).map { result ->
            when (result) {
                is Result.Success -> {
                    val userItems = result.data.userItems
                    if (userItems.isNotEmpty()) {
                        Result.Success(userItems)
                    } else {
                        Result.Error(IllegalStateException("UserItems is empty"))
                    }
                }
                is Result.Error -> result
                else -> Result.Error(IllegalStateException("Result must be Success or Error"))
            }
        }
    }
}
