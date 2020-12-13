package com.specialschool.schoolapp.ui.event

import com.specialschool.schoolapp.di.ApplicationScope
import com.specialschool.schoolapp.di.MainDispatcher
import com.specialschool.schoolapp.domain.bookmark.StarEventUseCase
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@InstallIn(ActivityComponent::class)
@Module
internal class EventActionsViewModelDelegateModule {

    @Provides
    fun provideEventActionsViewModelDelegate(
        signInViewModelDelegate: SignInViewModelDelegate,
        starEventUseCase: StarEventUseCase,
        @ApplicationScope externalScope: CoroutineScope,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): EventActionsViewModelDelegate {
        return DefaultEventActionsViewModelDelegate(
            signInViewModelDelegate, starEventUseCase, externalScope, dispatcher
        )
    }
}
