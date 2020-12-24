package com.specialschool.schoolapp.util

import androidx.lifecycle.Observer

/**
 * Event로 나타나는 data의 래퍼 클래스
 *
 * @property content data
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * 중복 실행을 방지하고 [content]를 반환한다.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * 중복 실행되었어도 [content]를 반환한다.
     */
    fun peekContent(): T = content
}

/**
 * [Event]를 위한 [Observer] 클래스, 중복 실행을 확인한다.
 *
 * [onEventUnhandledContent]는 [Event]가 실행되지 않은 경우에만 실행된다.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let{ value ->
            onEventUnhandledContent(value)
        }
    }
}
