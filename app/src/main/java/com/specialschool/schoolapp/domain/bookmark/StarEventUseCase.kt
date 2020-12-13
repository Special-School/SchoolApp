package com.specialschool.schoolapp.domain.bookmark

import com.specialschool.schoolapp.data.userevent.SchoolAndUserItemRepository
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.SuspendUseCase
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class StarEventUseCase @Inject constructor(
    private val repository: SchoolAndUserItemRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<StarEventParameter, StarUpdatedStatus>(dispatcher) {

    override suspend fun execute(parameters: StarEventParameter): StarUpdatedStatus {
        return when (val result = repository.starEvent(
            parameters.userId, parameters.userItem.userEvent
        )) {
            is Result.Success -> result.data
            is Result.Error -> throw result.exception
            else -> throw IllegalStateException()
        }
    }
}

data class StarEventParameter(
    val userId: String,
    val userItem: UserItem
)

enum class StarUpdatedStatus {
    STARRED,
    UNSTARRED
}
