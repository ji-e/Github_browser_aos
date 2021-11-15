package com.jieun.githubbrowser.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * date 2021-11-14
 * create by jieun
 */
@Entity
data class SearchRecentData(
    val keyword: String,    // 검색어
) {
    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0
}
