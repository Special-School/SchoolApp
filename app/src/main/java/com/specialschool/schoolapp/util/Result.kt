package com.specialschool.schoolapp.util

import com.specialschool.schoolapp.util.Result.Success

/**
 * 로딩 상태 값을 가지고 있는 클래스
 *
 * 대수적 합타입으로 표현함
 * @param <R> type of result
 */
sealed class Result<out R> {

    /**
     * 성공적으로 로딩했을 경우를 나타낸다.
     *
     * @property data 결과 값
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * 로딩 중 발생한 에러를 나타낸다.
     * @property exception 발생한 예외
     */
    data class Error(val exception: Exception) : Result<Nothing>()

    object Loading : Result<Nothing>()
}

/**
 * 성공한 경우 [Success.data]를 반환하고, 아니면 [fallback]을 반환한다.
 */
fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

/**
 * [Result] 객체가 [Success] 타입인지 확인하지 않고도 안전하게 [Success.data]를 반환한다.
 */
val <T> Result<T>.data: T?
    get() = (this as? Success)?.data
