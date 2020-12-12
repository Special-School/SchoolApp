package com.specialschool.schoolapp.data.signin

import com.google.firebase.auth.FirebaseAuth
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthStateUserDataSource @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthStateUserDataSource {

    override fun getBasicUserInfo(): Flow<Result<AuthenticatedUserInfo?>> {
        TODO("Not yet implemented")
    }
}
