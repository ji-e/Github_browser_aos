package com.jieun.githubbrowser.network

import com.jieun.githubbrowser.model.data.SearchUserData
import com.jieun.githubbrowser.model.data.UserEventsData
import com.jieun.githubbrowser.model.data.UserInfoData
import com.jieun.githubbrowser.model.data.UserReposData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * date 2021-11-13
 * create by jieun
 */
interface ApiService {

    // Github 사용자 검색
    @GET(NetworkConstants.SEARCH_USERS)
    suspend fun getGithubSearchUsers(@QueryMap mapString: Map<String, String>?): Response<SearchUserData?>

    // Github 사용자 정보
    @GET("${NetworkConstants.USERS}/{username}")
    suspend fun getGithubUsersInfo(@Path("username") username: String?): Response<UserInfoData?>

    // Github 사용자 저장소
    @GET("${NetworkConstants.USERS}/{username}${NetworkConstants.REPOS}")
    suspend fun getGithubUsersRepos(@Path("username") username: String?, @QueryMap mapString: Map<String, String>?): Response<List<UserReposData>?>

    // Github 사용자 이벤트
    @GET("${NetworkConstants.USERS}/{username}${NetworkConstants.EVENTS}")
    suspend fun getGithubUsersEvents(@Path("username") username: String?, @QueryMap mapString: Map<String, String>? ): Response<List<UserEventsData>?>

    // Github Url Data
    @GET("{method}")
    suspend fun getUrlData(@Path("method", encoded = true) method: String?):Response<Any>
}