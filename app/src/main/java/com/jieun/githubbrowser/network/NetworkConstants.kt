package com.jieun.githubbrowser.network

/**
 * date 2021-11-13
 * create by jieun
 */
object NetworkConstants {
    const val CONNECT_TIMEOUT = 15L
    const val WRITE_TIMEOUT = 15L
    const val READ_TIMEOUT = 15L

    const val HEADER_ACCEPT = "application/vnd.github.v3+json"
//    const val HEADER_AUTHORIZATION = "token " // 개인 토근 주석
    const val BASE_URL = "https://api.github.com/"
    const val USERS = "/users"
    const val REPOS = "/repos"
    const val EVENTS = "/events"
    const val SEARCH_USERS = "search/users"
}