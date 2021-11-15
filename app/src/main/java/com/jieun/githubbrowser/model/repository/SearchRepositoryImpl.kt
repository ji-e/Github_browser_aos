package com.jieun.githubbrowser.model.repository

import com.google.gson.JsonArray
import com.jieun.githubbrowser.model.data.*
import com.jieun.githubbrowser.model.datasource.SearchDatasource
import com.jieun.githubbrowser.model.room.SearchRecentDatabase
import retrofit2.Response

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 검색 구현체
 */
class SearchRepositoryImpl(private val searchDatasource: SearchDatasource) : SearchRepository {
    private val db = SearchRecentDatabase.getInstance()

    override suspend fun getSearchUserData(map: Map<String, String>): Response<SearchUserData?> {
       return searchDatasource.getSearchUserData(map)
    }

    override suspend fun getUserInfoData(username: String): Response<UserInfoData?> {
        return searchDatasource.getUserInfoData(username)
    }

    override suspend fun getUserReposData(username: String, map: Map<String, String>): Response<List<UserReposData>?> {
        return searchDatasource.getUserReposData(username, map)
    }

    override suspend fun getUserEventsData(username: String, map: Map<String, String>): Response<List<UserEventsData>?> {
        return searchDatasource.getUserEventsData(username, map)
    }

    override suspend fun getUrlData(method: String?): Response<Any> {
        return searchDatasource.getUrlData(method)
    }

    override suspend fun getSearchRecent(): List<SearchRecentData>? {
        db ?: return null
        return db.recentSearchDao().getAll()
    }

    override suspend fun insertSearchRecent(searchRecentData: SearchRecentData): List<SearchRecentData>? {
        db ?: return null
        db.recentSearchDao().deleteByKeyword(searchRecentData.keyword)
        db.recentSearchDao().insert(searchRecentData)
        return db.recentSearchDao().getAll()
    }

    override suspend fun deleteSearchRecent(searchRecentData: SearchRecentData): List<SearchRecentData>? {
        db ?: return null
        db.recentSearchDao().deleteByKeyword(searchRecentData.keyword)
        return db.recentSearchDao().getAll()
    }

    override suspend fun deleteAllSearchRecent(): List<SearchRecentData>? {
        db ?: return null
        db.recentSearchDao().deleteAll()
        return db.recentSearchDao().getAll()
    }
}