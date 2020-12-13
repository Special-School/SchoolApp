package com.specialschool.schoolapp.data.userevent

import com.google.firebase.firestore.FirebaseFirestore
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.bookmark.StarUpdatedStatus
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreUserEventDataSource @Inject constructor(
    val firestore: FirebaseFirestore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserEventDataSource {

    companion object {

    }

    override fun getObservableUserEvents(userId: String): Flow<List<UserEvent>> {
        TODO("Not yet implemented")
    }

    override fun getObservableUserEvent(userId: String, eventId: String): Flow<UserEvent> {
        TODO("Not yet implemented")
    }

    override fun getUserEvents(userId: String): List<UserEvent> {
        TODO("Not yet implemented")
    }

    override fun getUserEvent(userId: String, eventId: String): UserEvent? {
        TODO("Not yet implemented")
    }

    override suspend fun starEvent(
        userId: String,
        userEvent: UserEvent
    ): Result<StarUpdatedStatus> = withContext(dispatcher) {
        TODO("Not yet implemented")
    }
}

// FirebaseFirestore.schoolDataDocument()
