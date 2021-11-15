package com.jieun.githubbrowser.utils

/**
 * date 2021-11-13
 * create by jieun
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false

    /**
     * 중복적인 이벤트 처리를 방지
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
     * 이벤트 처리 여부에 상관없이 값을 반환
     */
    fun peekContent(): T = content
}