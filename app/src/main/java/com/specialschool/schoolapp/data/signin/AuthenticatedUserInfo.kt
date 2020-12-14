package com.specialschool.schoolapp.data.signin

import android.net.Uri

interface AuthenticatedUserInfo {

    fun isSignedIn(): Boolean

    fun getUid(): String?

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): Uri?
}
