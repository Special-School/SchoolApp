package com.specialschool.schoolapp.data.userevent

import com.specialschool.schoolapp.data.SchoolRepository
import com.specialschool.schoolapp.domain.bookmark.StarUpdatedStatus
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SchoolAndUserItemRepository {

    fun getObservableUserItems(
        userId: String?
    ): Flow<Result<ObservableUserItems>>

    fun getObservableUserItem(
        userId: String?,
        itemId: String
    ): Flow<Result<UserItem>>

    fun getUserEvents(userId: String?): List<UserEvent>

    suspend fun starEvent(userId: String, userEvent: UserEvent): Result<StarUpdatedStatus>

    fun getUserItem(userId: String, itemId: String): UserItem
}

data class ObservableUserItems(
    val userItems: List<UserItem>
)

class DefaultSchoolAndUserItemRepository @Inject constructor(
    private val userItemDataSource: UserItemDataSource,
    private val schoolRepository: SchoolRepository
) : SchoolAndUserItemRepository {

    override fun getObservableUserItems(userId: String?): Flow<Result<ObservableUserItems>> {
        TODO("Not yet implemented")
    }

    override fun getObservableUserItem(userId: String?, itemId: String): Flow<Result<UserItem>> {
        TODO("Not yet implemented")
    }

    override fun getUserEvents(userId: String?): List<UserEvent> {
        TODO("Not yet implemented")
    }

    override suspend fun starEvent(
        userId: String,
        userEvent: UserEvent
    ): Result<StarUpdatedStatus> = userItemDataSource.starEvent(userId, userEvent)

    override fun getUserItem(userId: String, itemId: String): UserItem {
        TODO("Not yet implemented")
    }
}
