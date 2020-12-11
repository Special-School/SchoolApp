package com.specialschool.schoolapp.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.specialschool.schoolapp.domain.search.SearchUseCase
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _navigateToSchoolDetailAction = MutableLiveData<Event<String>>()
    val navigateToSchoolDetailAction: LiveData<Event<String>> = _navigateToSchoolDetailAction

    private val _searchResults = MediatorLiveData<List<School>>()
    val searchResult: LiveData<List<School>> = _searchResults

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        if (query.length < 2) {
            onQueryCleared()
            return
        }
        executeSearch(query)
    }

    private fun executeSearch(query: String) {
        viewModelScope.launch {
            processSearchResult(searchUseCase(query.trim()))
        }
    }

    private fun processSearchResult(result: Result<List<School>>) {
        val searchResults = (result as? Result.Success)?.data ?: emptyList()
        _searchResults.value = searchResults
    }

    private fun onQueryCleared() {
        _searchResults.value = emptyList()
    }
}
