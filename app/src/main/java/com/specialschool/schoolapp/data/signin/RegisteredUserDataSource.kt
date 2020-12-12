package com.specialschool.schoolapp.data.signin

import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RegisteredUserDataSource {

    fun observeUserChanges(userId: String): Flow<Result<Boolean?>>
}
