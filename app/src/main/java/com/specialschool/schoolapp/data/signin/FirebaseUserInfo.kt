package com.specialschool.schoolapp.data.signin

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

/**
 * [FirebaseUser]의 정보를 사용하기 위해 [AuthenticatedUserInfo] 인터페이스를 통해 호출한다.
 *
 * @property firebaseUser
 */
class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfo {

    override fun isSignedIn(): Boolean = firebaseUser != null

    override fun getUid(): String? = firebaseUser?.uid

    override fun getEmail(): String? = firebaseUser?.email

    override fun getDisplayName(): String? = firebaseUser?.displayName

    override fun getPhotoUrl(): Uri? = firebaseUser?.photoUrl
}
