package com.specialschool.schoolapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.protobuf.LazyStringArrayList
import com.specialschool.schoolapp.domain.search.SearchUseCase
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.ui.event.EventActions
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.successOr
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(),
    EventActions {

    private val _navigateToSchoolDetailAction = MutableLiveData<Event<String>>()
    val navigateToSchoolDetailAction: LiveData<Event<String>> = _navigateToSchoolDetailAction

    private val _searchResults = MediatorLiveData<List<School>>()
    val searchResults: LiveData<List<School>> = _searchResults

    private var searchJob: Job? = null

    private var textQuery = ""

    fun onSearchQueryChanged(query: String) {
        val newQuery = query.trim().takeIf { it.length >= 2 } ?: ""
        if (textQuery != newQuery) {
            textQuery = newQuery
            executeSearch()
        }
    }

    private fun executeSearch() {
        searchJob?.cancel()

        if (textQuery.isEmpty()) {
            clearSearchResults()
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            processSearchResult(searchUseCase(textQuery))
        }
    }

    private fun processSearchResult(result: Result<List<School>>) {
        val schools = result.successOr(emptyList())
        _searchResults.value = schools
    }

    private fun clearSearchResults() {
        _searchResults.value = emptyList()
    }

    override fun openItemDetail(id: String) {
        _navigateToSchoolDetailAction.value = Event(id)
    }

    private val _test1 = MutableLiveData<String>().apply {
        value = "검색"
    }
    val test : LiveData<String> = _test1

    private fun detail(str:String){
        var bundle = Bundle()
        val searchdetail : SearchDetailFragment = SearchDetailFragment()

        bundle.putString("school_id",str)
        searchdetail.arguments = bundle
    }
    private fun map(str:String){
        var bundle = Bundle()
        val map : MapFragment = MapFragment()

        bundle.putString("school_id",str)
        map.arguments = bundle
    }






}
