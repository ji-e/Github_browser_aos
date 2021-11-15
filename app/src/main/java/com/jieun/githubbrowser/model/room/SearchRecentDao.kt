package com.jieun.githubbrowser.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jieun.githubbrowser.model.data.SearchRecentData

/**
 * date 2021-11-14
 * create by jieun
 *
 * 최근 검색어
 */
@Dao
interface SearchRecentDao {
    @Insert
    suspend fun insert(subwayStation: SearchRecentData)

    @Delete
    suspend fun delete(subwayStation: SearchRecentData)

    @Query("DELETE FROM SearchRecentData WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword: String)

    @Query("DELETE FROM SearchRecentData")
    suspend fun deleteAll()

    @Query("SELECT * FROM SearchRecentData  ORDER BY idx DESC")
    suspend fun getAll(): List<SearchRecentData>

}