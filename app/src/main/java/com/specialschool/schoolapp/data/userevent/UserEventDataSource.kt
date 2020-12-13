package com.specialschool.schoolapp.data.userevent

import com.specialschool.schoolapp.domain.bookmark.StarUpdatedStatus
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow

interface UserEventDataSource {

    fun getObservableUserEvents(userId: String): Flow<List<UserEvent>>

    fun getObservableUserEvent(userId: String, eventId: String): Flow<UserEvent>

    fun getUserEvents(userId: String): List<UserEvent>

    fun getUserEvent(userId: String, eventId: String): UserEvent?

    suspend fun starEvent(userId: String, userEvent: UserEvent): Result<StarUpdatedStatus>
}
