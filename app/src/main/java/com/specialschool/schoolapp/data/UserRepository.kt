package com.specialschool.schoolapp.data

import android.util.Log

class UserRepository(
    val userLocalDataSource: UserDataSource
) : UserDataSource {
    override fun getUsers(callback: UserDataSource.LoadDatasCallback) {
        userLocalDataSource.getUsers(object : UserDataSource.LoadDatasCallback {
            override fun onDatasLoaded(users: List<User>) {
                callback.onDatasLoaded(users)
                Log.d("UserRepository","onDatasLoaded")
            }

            override fun onDataNotAvailable() {
                Log.d("UserRepository","onDataNotAvailable")
            }
        })
    }

    companion object {
        private var INSTANCE: UserRepository? = null

        @JvmStatic fun getInstance(userLocalDataSource: UserDataSource): UserRepository {
            return INSTANCE ?: UserRepository(userLocalDataSource).apply { INSTANCE = this }
        }

        @JvmStatic fun destoryInstance() {
            INSTANCE = null
        }
    }
}
