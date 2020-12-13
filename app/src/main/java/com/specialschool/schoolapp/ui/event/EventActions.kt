package com.specialschool.schoolapp.ui.event

import com.specialschool.schoolapp.model.UserItem

interface EventActions {
    fun openItemDetail(id: String)
    fun onStarClicked(userItem: UserItem)
}
