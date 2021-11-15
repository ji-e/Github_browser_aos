package com.jieun.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jieun.githubbrowser.model.data.*
import com.jieun.githubbrowser.model.repository.SearchRepository
import com.jieun.githubbrowser.model.room.SearchRecentDatabase
import kotlinx.coroutines.*

/**
 * date 2021-11-13
 * create by jieun
 *
 * Github 사용자 검색
 */
class SearchUserViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    // 로딩
    private val _liveLoading = MutableLiveData<Boolean>(false)
    var liveLoading: LiveData<Boolean> = _liveLoading

    // 최근 검색 기록 목록
    private val _liveSearchRecent = MutableLiveData<MutableList<SearchRecentData?>?>()
    var liveSearchRecent: LiveData<MutableList<SearchRecentData?>?> = _liveSearchRecent

    // 사용자 검색 결과 목록
    private val _liveSearchUsersResult =
        MutableLiveData<MutableList<SearchUserData.SearchUserItemData?>?>()
    var liveSearchUserResult: LiveData<MutableList<SearchUserData.SearchUserItemData?>?> =
        _liveSearchUsersResult

    // 사용자 상세 정보
    private val _liveUserDetail = MutableLiveData<MutableList<UserDetail?>?>()
    var liveUserDetail: LiveData<MutableList<UserDetail?>?> = _liveUserDetail

    // 검색어
    private val _liveKeyword = MutableLiveData<String?>()
    var liveKeyword: LiveData<String?> = _liveKeyword

    // 사용자 상세 정보 TempList
    private var userDetailList = mutableListOf<UserDetail?>()

    // 사용자 검색 결과 목록 TempList
    private var searchUserResultList = mutableListOf<SearchUserData.SearchUserItemData?>()

    /** Github 사용자 검색*/
    private var searchUserPage = 1
    private var searchUserPerPage = 30
    private var searchIncomplete = false
    private var searchUserIsCalling = true
    fun getSearchUser(id: String?, isRefresh: Boolean) {
        if (isRefresh) {
            searchUserPage = 1
            searchIncomplete = false
            searchUserIsCalling = false
            searchUserResultList.clear()
        }

        if ((searchIncomplete || searchUserIsCalling) && !isRefresh) return
        _liveLoading.postValue(isRefresh)
        searchUserIsCalling = true
        viewModelScope.launch {
            val map = mapOf(
                "q" to id.toString(),
                "page" to searchUserPage.toString(),
                "per_page" to searchUserPerPage.toString()
            )
            val searchUser = searchRepository.getSearchUserData(map)
            if (searchUser.isSuccessful) {
                val searchUserData = searchUser.body()
                val searchUserItemData = searchUserData?.items
                searchUserResultList = searchUserResultList.filterNotNull().toMutableList()
                if (!searchUserItemData.isNullOrEmpty()) {
                    searchUserResultList.addAll(searchUserData.items)
                    if (searchUserItemData.size >= searchUserPerPage) {
                        searchUserResultList.add(null)
                    }
                }

                _liveSearchUsersResult.postValue(searchUserResultList)

                searchUserPage++
                searchIncomplete = searchUserData?.incomplete_results == true
                searchUserIsCalling = false
                _liveLoading.postValue(false)
            } else {
                searchUserIsCalling = false
                _liveLoading.postValue(false)
                setErrorDialog(searchUser.errorBody()?.string())
            }
        }
    }

    /** Github 사용자 정보*/
    fun getUserInfo(nickname: String) = viewModelScope.async {
        val userInfoList = mutableListOf<UserDetail?>()
        _liveLoading.postValue(true)

        val user = searchRepository.getUserInfoData(nickname)
        if (user.isSuccessful) {
            val userData = user.body()
            userInfoList.add(userData)

            _liveLoading.postValue(false)

            userInfoList
        } else {
            _liveLoading.postValue(false)
            setErrorDialog(user.errorBody()?.string())
        }
    }

    /** Github 사용자 저장소*/
    private var userRepPerPage = 3
    fun getUserRepos(nickname: String) = viewModelScope.async {
        val userReposList = mutableListOf<UserDetail?>()
        _liveLoading.postValue(true)

        val map = mapOf(
            "per_page" to userRepPerPage.toString()
        )
        val user = searchRepository.getUserReposData(nickname, map)

        if (user.isSuccessful) {
            val userRepos = user.body()
            if (userRepos.isNullOrEmpty()) {
                userReposList.add(UserDetailEmptyData("저장소가 없습니다."))
            } else {
                for (userRepo in userRepos) {
                    userReposList.add(userRepo)
                }
            }

            _liveLoading.postValue(false)

            userReposList
        } else {
            _liveLoading.postValue(false)
            setErrorDialog(user.errorBody()?.string())
        }
    }

    /** Github 사용자 이벤트*/
    private var userEventsPage = 1
    private var userEventsIsCalling = true
    private var userEventPerPage = 30
    private var userEventIsIncomplete = false
    fun getUserEvents(nickname: String, isRefresh: Boolean) = viewModelScope.async {
        val userEventsList = mutableListOf<UserDetail?>()

        if (isRefresh) {
            userEventsPage = 1
            userEventsIsCalling = false
            userEventIsIncomplete = false
        }

        if ((userEventsIsCalling || userEventIsIncomplete) && !isRefresh) {

        } else {

            userEventsIsCalling = true

            val map = mapOf(
                "page" to userEventsPage.toString(),
                "per_page" to userEventPerPage.toString()
            )
            val user = searchRepository.getUserEventsData(nickname, map)
            if (user.isSuccessful) {
                val userEvents = user.body()

                // 로딩 제거
                userDetailList = userDetailList.filterNotNull().toMutableList()

                if (userEvents.isNullOrEmpty()) {
                    if (userEventsPage == 1) {
                        // 첫번째 페이지에서 이벤트가 없는 경우
                        userEventsList.add(UserDetailEmptyData("최근 이벤트가 없습니다."))
                    }
                    userEventIsIncomplete = true
                } else {
                    for (userEvent in userEvents) {
                        // repo 정보를 가져와서 화면에 노출시키려 했으나 너무 느려서 주석처리
//                        try {
//                            (Dispatchers.IO){
//                                val repo = getRepoInfoData(URL(userEvent.repo?.url.toString()).path)
//                                val changeRepo = userEvent.repo?.copy(url = repo?.description)
//                                val changeUserEvent = userEvent.copy(repo = changeRepo)
//                                userEventsList.add(changeUserEvent)
//                            }
//                        } catch (e: Exception) {
//                            userEventsList.add(userEvent)
//                            e.printStackTrace()
//                        }
                        userEventsList.add(userEvent)
                    }


                    if (userEvents.size < userEventPerPage) {
                        userEventIsIncomplete = true
                    } else {
                        // 로딩 추가
                        userEventsList.add(null)
                    }

                }

                userEventsPage++
                userEventsIsCalling = false
                _liveLoading.postValue(false)

                userEventsList
            } else {
                userEventsIsCalling = false
                _liveLoading.postValue(false)
                setErrorDialog(user.errorBody()?.string())
            }
        }
    }

    /** Github 저장소 정보*/
    suspend fun getRepoInfoData(url: String): UserReposData? {
        val reposData = searchRepository.getUrlData(url)
        return if (reposData.isSuccessful) {
            Gson().fromJson(Gson().toJson(reposData.body()), UserReposData::class.java)
        } else {
            null
        }
    }

    /** Github 사용자 정보 상세*/
    fun getUserDetail(username: String) {
        userDetailList.clear()
        userEventsIsCalling = true
        viewModelScope.launch {
            val userInfoDiffer = getUserInfo(username)
            val userReposDiffer = getUserRepos(username)
            val userEventsDiffer = getUserEvents(username, true)

            val userInfo = userInfoDiffer.await()
            val userRepos = userReposDiffer.await()
            val userEvents = userEventsDiffer.await()

            userDetailList.apply {
                add(UserDetailTitleData("사용자 정보"))
                (userInfo as? List<UserDetail>)?.let { userDetailList.addAll(it) }
                userDetailList.add(UserDetailTitleData("사용자 저장소"))
                (userRepos as? List<UserDetail>)?.let { userDetailList.addAll(it) }
                userDetailList.add(UserDetailTitleData("사용자 이벤트"))
                (userEvents as? List<UserDetail>)?.let { userDetailList.addAll(it) }
            }
            _liveUserDetail.value = (userDetailList)
        }
    }

    /** Github 사용자 이벤트 페이징*/
    fun getPagingUserEvents(username: String) {
        viewModelScope.launch {
            val userEvents = getUserEvents(username, false).await()
            (userEvents as? List<UserDetail>)?.let { userDetailList.addAll(it) }
            _liveUserDetail.value = (userDetailList)
        }
    }

    /** 검색어 설정*/
    fun setKeyword(searchRecentData: SearchRecentData?) {
        _liveKeyword.value = searchRecentData?.keyword
    }

    /** 최근 검색어 목록*/
    fun getSearchRecent() = viewModelScope.launch {
        _liveSearchRecent.postValue(searchRepository.getSearchRecent()?.toMutableList())
    }

    /** 최근 검색어 삽입*/
    fun insertSearchRecent(searchRecentData: SearchRecentData) = viewModelScope.launch {
        getSearchUser(searchRecentData.keyword,true)
        _liveSearchRecent.postValue(searchRepository.insertSearchRecent(searchRecentData)?.toMutableList())
    }

    /** 최근 검색어 삭제*/
    fun deleteSearchRecent(searchRecentData: SearchRecentData) = viewModelScope.launch {
        _liveSearchRecent.postValue(searchRepository.deleteSearchRecent(searchRecentData)?.toMutableList())
    }

    /** 최근 검색어 목록 전체 삭제*/
    fun deleteAllSearchRecent() = viewModelScope.launch {
        _liveSearchRecent.postValue(searchRepository.deleteAllSearchRecent()?.toMutableList())
    }

}
