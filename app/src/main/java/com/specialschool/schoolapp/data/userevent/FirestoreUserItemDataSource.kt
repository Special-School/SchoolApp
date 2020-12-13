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

class FirestoreUserItemDataSource @Inject constructor(
    val firestore: FirebaseFirestore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserItemDataSource {

    companion object {

    }

    override fun getObservableUserItems(userId: String): Flow<List<UserItem>> {
        TODO("Not yet implemented")
    }

    override fun getObservableUserItem(userId: String, itemId: String): Flow<UserItem> {
        TODO("Not yet implemented")
    }

    override fun getUserItems(userId: String): List<UserItem> {
        TODO("Not yet implemented")
    }

    override fun getUserItem(userId: String, itemId: String): UserItem? {
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
