package com.specialschool.schoolapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate

class MainActivityViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate
) : ViewModel(),
    SignInViewModelDelegate by signInViewModelDelegate
