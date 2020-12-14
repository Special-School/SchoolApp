package com.specialschool.schoolapp.data.signin

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfo {

    override fun isSignedIn(): Boolean = firebaseUser != null

    override fun getUid(): String? = firebaseUser?.uid

    override fun getEmail(): String? = firebaseUser?.email

    override fun getDisplayName(): String? = firebaseUser?.displayName

    override fun getPhotoUrl(): Uri? = firebaseUser?.photoUrl
}
