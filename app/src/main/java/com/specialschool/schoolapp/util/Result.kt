package com.specialschool.schoolapp.util

import com.specialschool.schoolapp.util.Result.Success

/**
 * 결과를 로딩 상태와 함께 가지고 있는 클래스
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> Result<T>.successOf(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

val <T> Result<T>.data: T?
    get() = (this as? Success)?.data
