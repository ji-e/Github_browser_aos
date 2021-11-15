package com.jieun.githubbrowser.model.datasource

import com.google.gson.JsonArray
import com.jieun.githubbrowser.model.data.SearchUserData
import com.jieun.githubbrowser.model.data.UserEventsData
import com.jieun.githubbrowser.model.data.UserInfoData
import com.jieun.githubbrowser.model.data.UserReposData
import com.jieun.githubbrowser.network.ApiService
import retrofit2.Response

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 검색 구현체
 */
class SearchDatasourceImpl(private val apiService: ApiService) : SearchDatasource {
    override suspend fun getSearchUserData(map: Map<String, String>): Response<SearchUserData?> {
        return apiService.getGithubSearchUsers(map)
    }

    override suspend fun getUserInfoData(username: String): Response<UserInfoData?> {
        return apiService.getGithubUsersInfo(username)
    }

    override suspend fun getUserReposData(username: String, map: Map<String, String>): Response<List<UserReposData>?> {
        return apiService.getGithubUsersRepos(username, map)
    }

    override suspend fun getUserEventsData(username: String, map: Map<String, String>): Response<List<UserEventsData>?> {
        return apiService.getGithubUsersEvents(username, map)
    }

    override suspend fun getUrlData(method: String?): Response<Any> {
        return apiService.getUrlData(method)
    }
}