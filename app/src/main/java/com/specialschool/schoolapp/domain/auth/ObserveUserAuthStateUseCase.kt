package com.specialschool.schoolapp.domain.auth

import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.di.ApplicationScope
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.FlowUseCase
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ObserveUserAuthStateUseCase @Inject constructor(
    @ApplicationScope private val externalScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Any, AuthenticatedUserInfo>(ioDispatcher) {

    override fun execute(parameters: Any): Flow<Result<AuthenticatedUserInfo>> {
        TODO("Not yet implemented")
    }
}
