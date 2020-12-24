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
    EventActionsViewModelDelegate by eventActionsViewModelDelegate,
    SignInViewModelDelegate by signInViewModelDelegate {

    private var searchJob: Job? = null

    private val _searchResults = MediatorLiveData<List<UserItem>>()
    val searchResults: LiveData<List<UserItem>> = _searchResults

    private var textQuery = ""

    private val currentUserObserver = Observer<AuthenticatedUserInfo?> {
        executeSearch()
    }

    private val loadUserItemsResult = currentUserInfo.switchMap {
        loadUserItemsUseCase(getUserId()).asLiveData()
    }

    private val currentUserItemObserver = Observer<Result<List<UserItem>>> {
        refreshUserItems(it.data)
    }

    init {
        currentUserInfo.observeForever(currentUserObserver)
        loadUserItemsResult.observeForever(currentUserItemObserver)
    }

    override fun onCleared() {
        super.onCleared()
        currentUserInfo.removeObserver(currentUserObserver)
        loadUserItemsResult.removeObserver(currentUserItemObserver)
    }

    fun onSearchQueryChanged(query: String) {
        val newQuery = query.trim().takeIf { it.length >= 2 } ?: ""
        if (textQuery != newQuery) {
            textQuery = newQuery
            executeSearch()
        }
    }

    private fun executeSearch() {
        searchJob.cancelIfActive()

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
        _searchResults.value = loadUserItemsResult.value?.data ?: emptyList()
    }

    private fun refreshUserItems(items: List<UserItem>?) {
        _searchResults.value = items ?: emptyList()
    }
}
