package com.jieun.githubbrowser.model.data

/**
 * date 2021-11-13
 * create by jieun
 *
 * 사용자 정보
 */
data class UserInfoData(
    val login: String?,             // 로그인 아이디
    val id: Int?,                   // id
    val avatar_url: String?,        // 프로필 url
    val name: String?,              // 이름
    val bio: String?,               // 자기소개
    val followers: Int?,            // 팔로워 수
    val following: Int?,            // 팔로잉 수
) : UserDetail
