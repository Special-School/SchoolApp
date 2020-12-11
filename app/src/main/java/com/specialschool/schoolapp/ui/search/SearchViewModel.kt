package com.specialschool.schoolapp.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.util.Event
import kotlinx.coroutines.Job

class SearchViewModel @ViewModelInject constructor() : ViewModel() {

    private val _navigateToSchoolDetailAction = MutableLiveData<Event<String>>()
    val navigateToSchoolDetailAction: LiveData<Event<String>> = _navigateToSchoolDetailAction

    private val _searchResults = MediatorLiveData<List<School>>()
    val searchResult: LiveData<List<School>> = _searchResults

    private var searchJob: Job? = null

    private var textQuery = ""


}
