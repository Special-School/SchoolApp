package com.specialschool.schoolapp.data.userevent

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.specialschool.schoolapp.data.schoolDataDocument
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.bookmark.StarUpdatedStatus
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.Result.Success
import com.specialschool.schoolapp.util.Result.Error
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Firestore에 저장된 user data source
 */
@ExperimentalCoroutinesApi
class FirestoreUserEventDataSource @Inject constructor(
    val firestore: FirebaseFirestore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserEventDataSource {

    /**
     * 실시간 업데이트 리스너를 등록하고, User event 데이터 들을 가져오는 비동기 메서드.
     */
    override fun getObservableUserEvents(userId: String): Flow<List<UserEvent>> {
        if (userId.isEmpty()) {
            return flow { emit(emptyList()) }
        } else {
            return (channelFlow {
                val eventsCollection = firestore
                    .schoolDataDocument()
                    .collection("users")
                    .document(userId)
                    .collection("events")

                var currentValue: List<UserEvent>? = null

                val subscription = eventsCollection.addSnapshotListener { snapshot, _ ->
                    if (snapshot == null) {
                        return@addSnapshotListener
                    }
                    val result = snapshot.documents.map { parseUserEvent(it) }
                    currentValue = result
                    offer(result)
                }

                awaitClose { subscription.remove() }
            }).flowOn(Dispatchers.Main)
        }
    }

    /**
     * 실시간 업데이트 리스너를 등록하고, 특정 User event 데이터를 가져오는 비동기 메서드
     */
    override fun getObservableUserEvent(userId: String, eventId: String): Flow<UserEvent> {
        return (channelFlow<UserEvent> {
            val eventDocument = firestore
                .schoolDataDocument()
                .collection("users")
                .document(userId)
                .collection("events")
                .document(eventId)

            var currentValue: UserEvent? = null

            val subscription = eventDocument.addSnapshotListener { snapshot, _ ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                val result = if (snapshot.exists()) {
                    parseUserEvent(snapshot)
                } else {
                    UserEvent(id = eventId)
                }
                currentValue = result
                channel.offer(result)
            }

            awaitClose { subscription.remove() }
        }).flowOn(dispatcher)
    }

    // TODO: unused method
    override fun getUserEvents(userId: String): List<UserEvent> {
        if (userId.isEmpty()) {
            return emptyList()
        }

        val task = firestore
            .schoolDataDocument()
            .collection("users")
            .document(userId)
            .collection("events").get()
        val snapshot = Tasks.await(task, 20, TimeUnit.SECONDS)
        return snapshot.documents.map { parseUserEvent(it) }
    }

    // TODO: unused method
    override fun getUserEvent(userId: String, eventId: String): UserEvent? {
        if (userId.isEmpty()) {
            return null
        }

        val task = firestore
            .schoolDataDocument()
            .collection("users")
            .document(userId)
            .collection("events")
            .document(eventId).get()
        val snapshot = Tasks.await(task, 20, TimeUnit.SECONDS)
        return parseUserEvent(snapshot)
    }

    /**
     * Firestore에 즐겨찾기된 학교 정보를 업데이트한다.
     *
     * @return 실행 결과([StarUpdatedStatus])를 반환한다.
     */
    override suspend fun starEvent(
        userId: String,
        userEvent: UserEvent
    ): Result<StarUpdatedStatus> = withContext(dispatcher) {
        // 응답이 오기 전까진 lock
        suspendCancellableCoroutine<Result<StarUpdatedStatus>> { continuation ->
            val data = mapOf(
                "id" to userEvent.id,
                "isStarred" to userEvent.isStarred
            )

            firestore
                .schoolDataDocument()
                .collection("users")
                .document(userId)
                .collection("events")
                .document(userEvent.id).set(data, SetOptions.merge())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        continuation.resume(
                            Success(
                                if (userEvent.isStarred) StarUpdatedStatus.STARRED
                                else StarUpdatedStatus.UNSTARRED
                            )
                        )
                    } else {
                        continuation.resume(
                            Error(
                                it.exception ?: RuntimeException("Error updating star")
                            )
                        )
                    }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}
