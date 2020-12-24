package com.specialschool.schoolapp.data.signin

import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * 현재 사용자에 대한 업데이트가 발생하면, 인증 상태 데이터를 수신한다.
 *
 * @see [FirebaseAuthStateUserDataSource]
 */
interface AuthStateUserDataSource {

    fun getBasicUserInfo(): Flow<Result<AuthenticatedUserInfo?>>
}
