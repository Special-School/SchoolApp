package com.specialschool.schoolapp.data.signin

import com.google.firebase.auth.FirebaseAuth
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

/**
 * [FirebaseAuth]의 변경을 수신하는 [AuthStateUserDataSource]
 */
@ExperimentalCoroutinesApi
class FirebaseAuthStateUserDataSource @Inject constructor(
    val firebaseAuth: FirebaseAuth
) : AuthStateUserDataSource {

    private var isListening = false

    private var lastUid: String? = null


    // 모든 subscriber에 마지막으로 변경된 데이터를 송신하는 채널, multicast
    // TODO: Deprecated API 변경, ConflatedBroadcastChannel -> StateFlow
    private val channel = ConflatedBroadcastChannel<Result<AuthenticatedUserInfo>>()

    /**
     * @property listener 사용자 인증 데이터 업데이트를 감지하는 리스너
     */
    val listener: ((FirebaseAuth) -> Unit) = { auth ->
        lastUid = auth.uid

        if (!channel.isClosedForSend) {
            channel.offer(Result.Success(FirebaseUserInfo(auth.currentUser)))
        } else {
            unregisterListener()
        }
    }

    /**
     * @return emitAll(openSubscription())
     */
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
