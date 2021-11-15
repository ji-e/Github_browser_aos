package com.jieun.githubbrowser.model.data

import java.util.*

/**
 * date 2021-11-14
 * create by jieun
 *
 * 사용자 이벤트
 */
data class UserEventsData(
    val id: String?,                    // id
    val type: String?,                  // 타입
    val actor: UserEventsActorData?,    // 행위자
    val repo: UserEventsRepoData?,      // 저장소
    val payload:UserEventsPayloadData?, // payload
    val created_at: Date
) : UserDetail {

    data class UserEventsActorData(
        val id: Int?,                   // 행위자 id
        val login: String?,             // 행위자 아이디
        val display_login: String?,     // 행위자 아이디
        val gravatar_id: String?,
        val url: String?,
        val avatar_url: String?         // 행위자 프로필 이미지
    )

    data class UserEventsRepoData(
        val id: Int?,                   // 저장소 id
        val name: String?,              // 저장소 이름
        val url: String?                // 저장소 url
    )

    data class UserEventsPayloadData(
        val action:String?,
        val comment:Any?
    )
}
