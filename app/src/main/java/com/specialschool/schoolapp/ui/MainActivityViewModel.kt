package com.specialschool.schoolapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate
) : ViewModel(),
    SignInViewModelDelegate by signInViewModelDelegate {

//    init {
//        viewModelScope.launch {
//            currentFirebaseUser.collect()
//        }
//    }
}
