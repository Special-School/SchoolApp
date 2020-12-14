package com.specialschool.schoolapp.domain.auth

import com.specialschool.schoolapp.data.signin.AuthStateUserDataSource
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.data.signin.FirebaseUserInfo
import com.specialschool.schoolapp.di.DefaultDispatcher
import com.specialschool.schoolapp.domain.FlowUseCase
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.Result.Error
import com.specialschool.schoolapp.util.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
open class ObserveUserAuthStateUseCase @Inject constructor(
    private val authStateUserDataSource: AuthStateUserDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Any, AuthenticatedUserInfo?>(dispatcher) {

    override fun execute(parameters: Any): Flow<Result<AuthenticatedUserInfo?>> =
        authStateUserDataSource.getBasicUserInfo().map { result ->
            if (result is Success) {
                Success(result.data)
            } else {
                Error(Exception("FirebaseAuth error"))
            }
        }
}
