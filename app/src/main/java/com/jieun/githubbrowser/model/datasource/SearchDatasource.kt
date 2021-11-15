package com.jieun.githubbrowser.model.datasource

import com.google.gson.JsonArray
import com.jieun.githubbrowser.model.data.SearchUserData
import com.jieun.githubbrowser.model.data.UserEventsData
import com.jieun.githubbrowser.model.data.UserInfoData
import com.jieun.githubbrowser.model.data.UserReposData
import retrofit2.Response

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 검색
 */
interface SearchDatasource {
    /** Github 사용자 검색*/
    suspend fun getSearchUserData(map: Map<String, String>): Response<SearchUserData?>

    /** Github 사용자 정보*/
    suspend fun getUserInfoData(username:String):Response<UserInfoData?>

    /** Github 사용자 저장소*/
    suspend fun getUserReposData(username:String, map: Map<String, String>):Response<List<UserReposData>?>

    /** Github 사용자 이벤트*/
    suspend fun getUserEventsData(username:String,map: Map<String, String>):Response<List<UserEventsData>?>

    /** Github Url*/
    suspend fun getUrlData(method:String?):Response<Any>
}