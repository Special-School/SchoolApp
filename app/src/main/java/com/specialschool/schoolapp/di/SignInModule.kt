package com.specialschool.schoolapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.specialschool.schoolapp.data.signin.AuthStateUserDataSource
import com.specialschool.schoolapp.data.signin.FirebaseAuthStateUserDataSource
import com.specialschool.schoolapp.util.signin.FirebaseAuthSignInHandler
import com.specialschool.schoolapp.util.signin.SignInHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
internal class SignInModule {

    @Provides
    fun provideSignInHandler(
        @ApplicationScope scope: CoroutineScope
    ): SignInHandler = FirebaseAuthSignInHandler(scope)

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideAuthStateUserDataSource(
        firebaseAuth: FirebaseAuth
    ): AuthStateUserDataSource {
        return FirebaseAuthStateUserDataSource(firebaseAuth)
    }
}
