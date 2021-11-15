package com.jieun.githubbrowser.model.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jieun.githubbrowser.GlobalApplication
import com.jieun.githubbrowser.model.data.SearchRecentData

/**
 * date 2021-11-14
 * create by jieun
 */
@Database(entities = [SearchRecentData::class], version = 1)
abstract class SearchRecentDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): SearchRecentDao

    companion object {
        private var instance: SearchRecentDatabase? = null

        @Synchronized
        fun getInstance(): SearchRecentDatabase? {
            if (instance == null) {
                synchronized(SearchRecentDatabase::class) {
                    instance = GlobalApplication.instance.applicationContext.let {
                        Room.databaseBuilder(
                            it,
                            SearchRecentDatabase::class.java,
                            "recent-database"
                        ).build()
                    }
                }
            }
            return instance
        }
    }
}