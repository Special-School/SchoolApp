package com.specialschool.schoolapp.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.di.MainDispatcher
import com.specialschool.schoolapp.domain.auth.ObserveUserAuthStateUseCase
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.Result.Error
import com.specialschool.schoolapp.util.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SignInViewModelDelegate {

    val currentFirebaseUser: Flow<Result<AuthenticatedUserInfo?>>

    val currentUserInfo: LiveData<AuthenticatedUserInfo?>

    val performSignInEvent: MutableLiveData<Event<SignInEvent>>

    suspend fun emitSignInRequest()

    suspend fun emitSignOutRequest()

    fun observeSignedInUser(): LiveData<Boolean>

    fun isSignedIn(): Boolean

    fun getUserId(): String?
}

enum class SignInEvent {
    REQUEST_SIGN_IN, REQUEST_SIGN_OUT
}

@ExperimentalCoroutinesApi
internal class FirebaseSignInViewModelDelegate @Inject constructor(
    observeUserAuthStateUseCase: ObserveUserAuthStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : SignInViewModelDelegate {

    override val currentFirebaseUser: Flow<Result<AuthenticatedUserInfo?>> =
        observeUserAuthStateUseCase(Any()).map {
            if (it is Error) {
                Log.e("", "", it.exception)
            }
            it
        }

    override val currentUserInfo: LiveData<AuthenticatedUserInfo?> = currentFirebaseUser.map {
        (it as? Success)?.data
    }.asLiveData()

    override val performSignInEvent = MutableLiveData<Event<SignInEvent>>()

    private val isSignedIn: LiveData<Boolean> = currentUserInfo.map {
        it?.isSignedIn() ?: false
    }

    override suspend fun emitSignInRequest() = withContext(dispatcher) {
        performSignInEvent.value = Event(SignInEvent.REQUEST_SIGN_IN)
    }

    override suspend fun emitSignOutRequest() = withContext(dispatcher) {
        performSignInEvent.value = Event(SignInEvent.REQUEST_SIGN_OUT)
    }

    override fun observeSignedInUser(): LiveData<Boolean> = isSignedIn

    override fun isSignedIn(): Boolean = isSignedIn.value == true

    override fun getUserId(): String? {
        return currentUserInfo.value?.getUid()
    }
}
