package com.jieun.githubbrowser.model.data

/**
 * date 2021-11-14
 * create by jieun
 *
 * 사용자 상세 정보
 */

interface UserDetail{
    override fun equals(other: Any?): Boolean
}

data class UserDetailTitleData(
    val title: String? = null,
) : UserDetail

data class UserDetailEmptyData(
    val content: String? = null,
) : UserDetail