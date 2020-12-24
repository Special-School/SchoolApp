package com.specialschool.schoolapp.ui.event

import com.specialschool.schoolapp.model.UserItem

/**
 * 사용자 이벤트 들을 나타내는 인터페이스
 */
interface EventActions {
    fun openItemDetail(id: String)
    fun onStarClicked(userItem: UserItem)
}
