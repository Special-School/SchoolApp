package com.specialschool.schoolapp.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.domain.schooldata.LoadUserItemsUseCase
import com.specialschool.schoolapp.domain.search.SearchParameter
import com.specialschool.schoolapp.domain.search.SearchUseCase
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.ui.event.EventActionsViewModelDelegate
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.cancelIfActive
import com.specialschool.schoolapp.util.data
import com.specialschool.schoolapp.util.successOr
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    eventActionsViewModelDelegate: EventActionsViewModelDelegate,
    private val searchUseCase: SearchUseCase,
    private val loadUserItemsUseCase: LoadUserItemsUseCase
) : ViewModel(),
    SignInViewModelDelegate by signInViewModelDelegate,
    EventActionsViewModelDelegate by eventActionsViewModelDelegate {

    private var loadUserItemsJob: Job? = null

    private var searchJob: Job? = null

    private val _searchResults = MediatorLiveData<List<UserItem>>()

    val searchResults: LiveData<List<UserItem>> = _searchResults

    private var textQuery = ""

    private val currentUserObserver = Observer<AuthenticatedUserInfo?> {
        executeSearch()
    }

    init {
        viewModelScope.launch {
            currentFirebaseUser.collect {
                refreshUserItems()
            }
        }
        currentUserInfo.observeForever(currentUserObserver)
    }

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
            searchUseCase(
                SearchParameter(getUserId(), textQuery)
            ).collect {
                processSearchResult(it)
            }
        }
    }

    private fun processSearchResult(result: Result<List<UserItem>>) {
        if (result is Result.Loading) {
            return // avoids UI flickering
        }
        val userItems = result.successOr(emptyList())
        _searchResults.value = userItems
    }

    private fun clearSearchResults() {
        refreshUserItems()
        //_searchResults.value = emptyList()
    }

    private fun refreshUserItems() {
        loadUserItemsJob.cancelIfActive()

        loadUserItemsJob = viewModelScope.launch {
            loadUserItemsUseCase(getUserId()).collect {
                _searchResults.value = it.data
            }
        }
    }

    private val _test1 = MutableLiveData<String>().apply {
        value = "검색"
    }
    val test : LiveData<String> = _test1
}
