package com.specialschool.schoolapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.specialschool.schoolapp.domain.schooldata.LoadUserItemsUseCase
import com.specialschool.schoolapp.model.Coordinate
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.Result
import com.specialschool.schoolapp.util.data
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    private val loadUserItemsUseCase: LoadUserItemsUseCase
) : ViewModel(),
    HomeItemEventListener,
    SignInViewModelDelegate by signInViewModelDelegate {

    private val _itemResults = MediatorLiveData<List<Any>>()
    val itemResults: LiveData<List<Any>> = _itemResults

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignInDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignInDialogAction

    private val _navigateToSignOutDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignOutDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignOutDialogAction

    private val _navigateToMapAction = MutableLiveData<Event<Coordinate>>()
    val navigateToMapAction: LiveData<Event<Coordinate>> = _navigateToMapAction

    private val _navigateToEventAction = MutableLiveData<Event<String>>()
    val navigateToEventAction: LiveData<Event<String>>
        get() = _navigateToEventAction

    private val loadUserStarredItemResult = currentUserInfo.switchMap {
        loadUserItemsUseCase(getUserId()).asLiveData()
    }

    private val currentUserStarredItemObserver = Observer<Result<List<UserItem>>> {
        refreshIsStarredItems(it.data)
    }

    init {
        viewModelScope.launch {
            currentFirebaseUser.collect()
        }

        loadUserStarredItemResult.observeForever(currentUserStarredItemObserver)
    }

    override fun onCleared() {
        super.onCleared()
        loadUserStarredItemResult.removeObserver(currentUserStarredItemObserver)
    }

    fun onProfileButtonClicked() {
        if (isSignedIn()) {
            _navigateToSignOutDialogAction.value = Event(Unit)
        } else {
            _navigateToSignInDialogAction.value = Event(Unit)
        }
    }

    private fun refreshIsStarredItems(items: List<UserItem>?) {
        _itemResults.value = items?.filter { it.userEvent.isStarred } ?: emptyList()
    }

    override fun openItemDetail(id: String) {
        _navigateToEventAction.value = Event(id)
    }

    override fun openItemMap(coordinate: Coordinate) {
        _navigateToMapAction.value = Event(coordinate)
    }
}

interface HomeItemEventListener {
    fun openItemDetail(id: String)
    fun openItemMap(coordinate: Coordinate)
}
