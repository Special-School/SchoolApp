package com.specialschool.schoolapp.data.signin

import com.google.firebase.firestore.FirebaseFirestore
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FireStoreRegisteredUserDataSource @Inject constructor(
    val fireStore: FirebaseFirestore
) : RegisteredUserDataSource {

    override fun observeUserChanges(userId: String): Flow<Result<Boolean?>> {
        TODO("Not yet implemented")
    }
}
