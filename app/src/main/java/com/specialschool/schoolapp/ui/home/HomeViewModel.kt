package com.specialschool.schoolapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel @ViewModelInject constructor() : ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = "home"
    }
    val title: LiveData<String> = _title
}
