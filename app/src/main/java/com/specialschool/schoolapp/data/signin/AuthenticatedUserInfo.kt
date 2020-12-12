package com.specialschool.schoolapp.data.signin

interface AuthenticatedUserInfo : AuthenticatedUserInfoBasic, AuthenticatedUserInfoRegistered

interface AuthenticatedUserInfoBasic {

    fun isSignedIn(): Boolean

    fun getUid(): String?
}

interface AuthenticatedUserInfoRegistered {

    fun isRegistered(): Boolean
}
