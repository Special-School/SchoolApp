package com.specialschool.schoolapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.specialschool.schoolapp.ui.event.EventActionsViewModelDelegate
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Event

class HomeViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    eventActionsViewModelDelegate: EventActionsViewModelDelegate
) : ViewModel(),
    SignInViewModelDelegate by signInViewModelDelegate,
    EventActionsViewModelDelegate by eventActionsViewModelDelegate {

    private val _title = MutableLiveData<String>().apply {
        value = "home"
    }
    val title: LiveData<String> = _title

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    override val navigateToSignInDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignInDialogAction

    fun onClickButton() {
        _title.value = "change"
        _navigateToSignInDialogAction.value = Event(Unit)
    }
}
