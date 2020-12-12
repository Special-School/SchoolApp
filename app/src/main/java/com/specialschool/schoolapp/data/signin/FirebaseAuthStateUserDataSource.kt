package com.specialschool.schoolapp.data.signin

import com.google.firebase.auth.FirebaseAuth
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FirebaseAuthStateUserDataSource @Inject constructor(
    val firebaseAuth: FirebaseAuth
) : AuthStateUserDataSource {

    private var isListening = false

    private var lastUid: String? = null

    private val channel = ConflatedBroadcastChannel<Result<AuthenticatedUserInfo>>()

    val listener: ((FirebaseAuth) -> Unit) = { auth ->
        lastUid = auth.uid

        if (!channel.isClosedForSend) {
            channel.offer(Result.Success(FirebaseUserInfo(auth.currentUser)))
        } else {
            unregisterListener()
        }
    }

    @FlowPreview
    @Synchronized
    override fun getBasicUserInfo(): Flow<Result<AuthenticatedUserInfo?>> {
        if (!isListening) {
            firebaseAuth.addAuthStateListener(listener)
            isListening = true
        }
        return channel.asFlow()
    }

    private fun unregisterListener() {
        firebaseAuth.removeAuthStateListener(listener)
    }
}
