package com.specialschool.schoolapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.domain.schooldata.LoadUserItemsUseCase
import com.specialschool.schoolapp.model.Coordinate
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.ui.event.EventActions
import com.specialschool.schoolapp.ui.event.EventActionsViewModelDelegate
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.cancelIfActive
import com.specialschool.schoolapp.util.data
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    private val loadUserItemsUseCase: LoadUserItemsUseCase
) : ViewModel(),
    HomeItemEventListener,
    SignInViewModelDelegate by signInViewModelDelegate {

    private var loadUserItemsJob: Job? = null

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

    private val currentUserObserver = Observer<AuthenticatedUserInfo?> {
        refreshIsStarredItems()
    }

    init {
        viewModelScope.launch {
            // TODO: Fix crash -> JobCancellationException
            currentFirebaseUser.collect {
                refreshIsStarredItems()
            }
        }

        currentUserInfo.observeForever(currentUserObserver)
    }

    override fun onCleared() {
        super.onCleared()
        currentUserInfo.removeObserver(currentUserObserver)
    }

    fun onProfileButtonClicked() {
        if (isSignedIn()) {
            _navigateToSignOutDialogAction.value = Event(Unit)
        } else {
            _navigateToSignInDialogAction.value = Event(Unit)
        }
    }

    private fun refreshIsStarredItems() {
        loadUserItemsJob.cancelIfActive()

        loadUserItemsJob = viewModelScope.launch {
            loadUserItemsUseCase(getUserId()).collect { items ->
                _itemResults.value = items.data?.filter { item ->
                    item.userEvent.isStarred
                }
            }
        }
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
