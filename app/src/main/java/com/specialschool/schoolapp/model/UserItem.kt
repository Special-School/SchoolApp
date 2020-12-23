package com.specialschool.schoolapp.model

/**
 * 사용자 데이터 model, 학교 데이터와 사용자 이벤트 병합
 */
data class UserItem(
    val school: School,
    val userEvent: UserEvent
)
