package com.jieun.githubbrowser.module


import com.jieun.githubbrowser.model.datasource.SearchDatasource
import com.jieun.githubbrowser.model.datasource.SearchDatasourceImpl
import org.koin.dsl.module

/**
 * date 2021-11-13
 * create by jieun
 */
val dataSourceModule = module {
    single<SearchDatasource> { SearchDatasourceImpl(get()) }
}