package com.jieun.githubbrowser.model.data

/**
 * date 2021-11-14
 * create by jieun
 *
 * 사용자 저장소
 */
data class UserReposData(
    val id: Int?,                   // id
    val name:String?,               // 이름
    val full_name:String?,          // 전체 이름
    val description:String?,        // 설명
    val language:String?,           // 언어
    val stargazers_count:Int?,      // star 갯수
):UserDetail
