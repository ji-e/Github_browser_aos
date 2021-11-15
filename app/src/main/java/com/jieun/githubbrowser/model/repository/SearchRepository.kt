package com.jieun.githubbrowser.model.repository

import com.jieun.githubbrowser.model.data.*
import retrofit2.Response

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 검색
 */
interface SearchRepository {
    /** Github 사용자 검색*/
    suspend fun getSearchUserData(map: Map<String, String>): Response<SearchUserData?>

    /** Github 사용자 정보*/
    suspend fun getUserInfoData(username:String):Response<UserInfoData?>

    /** Github 사용자 저장소*/
    suspend fun getUserReposData(username:String, map: Map<String, String>):Response<List<UserReposData>?>

    /** Github 사용자 이벤트*/
    suspend fun getUserEventsData(username:String, map: Map<String, String>):Response<List<UserEventsData>?>

    /** Github Url*/
    suspend fun getUrlData(method:String?):Response<Any>

    /** 최근 검색어 목록*/
    suspend fun getSearchRecent():List<SearchRecentData>?

    /** 최근 검색어 삽입*/
    suspend fun insertSearchRecent(searchRecentData: SearchRecentData):List<SearchRecentData>?

    /** 최근 검색어 삭제*/
    suspend fun deleteSearchRecent(searchRecentData: SearchRecentData):List<SearchRecentData>?

    /** 최근 검색어 전체 삭제*/
    suspend fun deleteAllSearchRecent():List<SearchRecentData>?

}