package com.specialschool.schoolapp.model

/**
 * 사용자 이벤트 model
 *
 * @property id 학교 데이터 id
 * @property isStarred 즐겨찾기 확인
 */
data class UserEvent(
    val id: String,
    val isStarred: Boolean = false
)
