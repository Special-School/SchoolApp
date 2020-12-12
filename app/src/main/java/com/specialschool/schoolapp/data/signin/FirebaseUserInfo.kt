package com.specialschool.schoolapp.data.signin

import com.google.firebase.auth.FirebaseUser

class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfo {

    override fun isSignedIn(): Boolean = firebaseUser != null

    override fun getUid(): String? = firebaseUser?.uid
}
