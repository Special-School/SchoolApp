package com.specialschool.schoolapp.util

import androidx.lifecycle.Observer

/**
 * event wrapper 클래스로 여러 observer에서 동일한 event에 접근하는 경우 중복 실행을 방지한다
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

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
