package com.specialschool.schoolapp.data.signin

interface AuthenticatedUserInfo {

    fun isSignedIn(): Boolean

    fun getUid(): String?
}
