package com.specialschool.schoolapp.data

interface UserDataSource {

    interface LoadDatasCallback {
        fun onDatasLoaded(users: List<User>)
        fun onDataNotAvailable()
    }

    fun getUsers(callback: LoadDatasCallback)
}
