package com.specialschool.schoolapp.ui.signin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialschool.schoolapp.util.Event
import kotlinx.coroutines.launch

class SignInViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate
) : ViewModel(), SignInViewModelDelegate by signInViewModelDelegate {

    private val _dismissDialogAction = MutableLiveData<Event<Unit>>()
    val dismissDialogAction: LiveData<Event<Unit>>
        get() = _dismissDialogAction

    fun onSignIn() {
        viewModelScope.launch {
            emitSignInRequest()
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            emitSignOutRequest()
        }
    }

    fun onCancel() {
        _dismissDialogAction.value = Event(Unit)
    }
}
