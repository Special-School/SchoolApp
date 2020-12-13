package com.specialschool.schoolapp.data.userevent

import com.specialschool.schoolapp.domain.bookmark.StarUpdatedStatus
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow

interface UserItemDataSource {

    fun getObservableUserItems(userId: String): Flow<List<UserItem>>

    fun getObservableUserItem(userId: String, itemId: String): Flow<UserItem>

    fun getUserItems(userId: String): List<UserItem>

    fun getUserItem(userId: String, itemId: String): UserItem?

    suspend fun starEvent(userId: String, userEvent: UserEvent): Result<StarUpdatedStatus>
}
