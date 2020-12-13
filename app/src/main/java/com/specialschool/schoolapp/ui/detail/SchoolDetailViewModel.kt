package com.specialschool.schoolapp.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.specialschool.schoolapp.domain.schooldata.LoadUserItemUseCase
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.model.UserEvent
import com.specialschool.schoolapp.ui.event.EventActionsViewModelDelegate
import com.specialschool.schoolapp.ui.signin.SignInViewModelDelegate
import com.specialschool.schoolapp.util.cancelIfActive
import com.specialschool.schoolapp.util.data
import com.specialschool.schoolapp.util.setValueIfNew
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SchoolDetailViewModel @ViewModelInject constructor(
    private val signInViewModelDelegate: SignInViewModelDelegate,
    private val eventActionsViewModelDelegate: EventActionsViewModelDelegate,
    private val loadUserItemUseCase: LoadUserItemUseCase,
) : ViewModel(),
    EventActionsViewModelDelegate by eventActionsViewModelDelegate,
    SignInViewModelDelegate by signInViewModelDelegate {

    private var loadUserItemJob: Job? = null

    private val _school = MediatorLiveData<School>()
    val school: LiveData<School> = _school

    private val _userEvent = MediatorLiveData<UserEvent>()
    val userEvent: LiveData<UserEvent> = _userEvent

    private val schoolId = MutableLiveData<String?>()

    init {
        _userEvent.addSource(currentUserInfo) {
            refreshUserItem()
        }

        _school.addSource(schoolId) {
            refreshUserItem()
        }
    }

    private fun refreshUserItem() {
        getSchoolId()?.let {
            listenForUserItemChanges(it)
        }
    }

    private fun listenForUserItemChanges(schoolId: String) {
        loadUserItemJob.cancelIfActive()

        loadUserItemJob = viewModelScope.launch {
            loadUserItemUseCase(getUserId() to schoolId).collect {
                val result = it.data ?: return@collect
                val school = result.school

                _school.value = school
                _userEvent.value = result.userEvent
                // Snackbar
            }
        }
    }

    fun setSchoolId(newSchoolId: String?) {
        schoolId.setValueIfNew(newSchoolId)
    }

    private fun getSchoolId(): String? {
        return schoolId.value
    }
}
