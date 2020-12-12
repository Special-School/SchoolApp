package com.specialschool.schoolapp.data.signin

import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthStateUserDataSource {

    fun getBasicUserInfo(): Flow<Result<AuthenticatedUserInfoBasic?>>
}
