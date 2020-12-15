package com.specialschool.schoolapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.ui.event.EventActionsViewModelDelegate
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Event

class HomeViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    eventActionsViewModelDelegate: EventActionsViewModelDelegate
) : ViewModel(),
    SignInViewModelDelegate by signInViewModelDelegate,
    EventActionsViewModelDelegate by eventActionsViewModelDelegate {

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    override val navigateToSignInDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignInDialogAction

    private val _navigateToSignOutDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignOutDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignOutDialogAction

    private val currentUserObserver = Observer<AuthenticatedUserInfo?> {

    }

    init {
        currentUserInfo.observeForever(currentUserObserver)
    }

    override fun onCleared() {
        super.onCleared()
        currentUserInfo.observeForever(currentUserObserver)
    }

    fun onProfileButtonClicked() {
        if (isSignedIn()) {
            _navigateToSignOutDialogAction.value = Event(Unit)
        } else {
            _navigateToSignInDialogAction.value = Event(Unit)
        }
    }
}
