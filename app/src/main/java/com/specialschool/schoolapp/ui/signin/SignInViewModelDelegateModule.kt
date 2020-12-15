package com.specialschool.schoolapp.ui.signin

import com.specialschool.schoolapp.di.MainDispatcher
import com.specialschool.schoolapp.domain.auth.ObserveUserAuthStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class SignInViewModelDelegateModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideSignInViewModelDelegate(
        observeUserAuthStateUseCase: ObserveUserAuthStateUseCase,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): SignInViewModelDelegate {
        return FirebaseSignInViewModelDelegate(
            observeUserAuthStateUseCase, dispatcher
        )
    }
}
