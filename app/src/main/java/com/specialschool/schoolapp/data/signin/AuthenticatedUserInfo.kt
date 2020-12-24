package com.specialschool.schoolapp.data.signin

import android.net.Uri

/**
 * 파이어베이스와 분리된 사용자 정보 인터페이스
 *
 * @see [FirebaseUserInfo]
 */
interface AuthenticatedUserInfo {

    fun isSignedIn(): Boolean

    fun getUid(): String?

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): Uri?
}
