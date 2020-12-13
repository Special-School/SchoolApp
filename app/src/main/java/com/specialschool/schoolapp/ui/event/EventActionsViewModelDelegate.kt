package com.specialschool.schoolapp.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.specialschool.schoolapp.di.ApplicationScope
import com.specialschool.schoolapp.di.MainDispatcher
import com.specialschool.schoolapp.domain.bookmark.StarEventParameter
import com.specialschool.schoolapp.domain.bookmark.StarEventUseCase
import com.specialschool.schoolapp.model.UserItem
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.Event
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface EventActionsViewModelDelegate : EventActions {
    val navigateToEventAction: LiveData<Event<String>>
    val navigateToSignInDialogAction: LiveData<Event<Unit>>
    // snack bar message
}

class DefaultEventActionsViewModelDelegate @Inject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    private val starEventUseCase: StarEventUseCase,
    @ApplicationScope private val externalScope: CoroutineScope,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : EventActionsViewModelDelegate, SignInViewModelDelegate by signInViewModelDelegate {

    private val _navigateToEventAction = MutableLiveData<Event<String>>()
    override val navigateToEventAction: LiveData<Event<String>>
        get() = _navigateToEventAction

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    override val navigateToSignInDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignInDialogAction

    override fun openItemDetail(id: String) {
        _navigateToEventAction.value = Event(id)
    }

    override fun onStarClicked(userItem: UserItem) {
        if (!isSignedIn()) {
            _navigateToSignInDialogAction.value = Event(Unit)
            return
        }
        val newIsStarredState = !userItem.userEvent.isStarred
        // snack bar message

        externalScope.launch(dispatcher) {
            getUserId()?.let {
                val result = starEventUseCase(
                    StarEventParameter(
                        it, userItem.copy(
                            userEvent = userItem.userEvent.copy(isStarred = newIsStarredState)
                        )
                    )
                )

                if (result is Result.Error) {
                    // snack bar message
                }
            }
        }
    }
}
