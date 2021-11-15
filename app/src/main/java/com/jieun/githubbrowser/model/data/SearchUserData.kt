package com.jieun.githubbrowser.model.data

/**
 * date 2021-11-13
 * create by jieun
 *
 * 사용자 검색
 */
data class SearchUserData(
    val total_count: Int?,
    val incomplete_results: Boolean?,
    val items: MutableList<SearchUserItemData>?
) {
    data class SearchUserItemData(
        val login: String?,             // 로그인 아이디
        val id: Int?,                   // id
        val avatar_url: String?,        // 프로필 url
        val score: Float?               // score
    )
}

