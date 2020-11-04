package com.specialschool.schoolapp.data

import androidx.annotation.VisibleForTesting
import com.specialschool.schoolapp.util.AppExecutors

class UserLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val userDao: UserDao
) : UserDataSource {
    override fun getUsers(callback: UserDataSource.LoadDatasCallback) {
        appExecutors.diskIO.execute {
            val users = userDao.getAll()
            appExecutors.mainThread.execute {
                if (users.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onDatasLoaded(users)
                }
            }
        }
    }

    companion object {
        private var INSTANCE: UserLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, userDao: UserDao): UserLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UserLocalDataSource::javaClass) {
                    INSTANCE = UserLocalDataSource(appExecutors, userDao)
                }
            }
            return INSTANCE!!;
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
