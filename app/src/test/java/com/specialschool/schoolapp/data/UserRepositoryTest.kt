package com.specialschool.schoolapp.data

import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    private lateinit var usersRepository: UserRepository

    @Mock private lateinit var userLocalDataSource: UserDataSource
    @Mock private lateinit var loadDatasCallback: UserDataSource.LoadDatasCallback

    @Before fun setup() {
        MockitoAnnotations.initMocks(this)
        usersRepository = UserRepository.getInstance(userLocalDataSource)
    }

    @After fun tearDown() {
        UserRepository.destoryInstance()
    }

    @Test fun `테스트 메서드`() {
        usersRepository.getUsers(loadDatasCallback)

        verify(userLocalDataSource).getUsers(any<UserDataSource.LoadDatasCallback>())
    }
}

fun <T> any(): T = Mockito.any<T>()
