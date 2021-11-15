package com.jieun.githubbrowser.module


import com.jieun.githubbrowser.model.repository.SearchRepository
import com.jieun.githubbrowser.model.repository.SearchRepositoryImpl
import org.koin.dsl.module

/**
 * date 2021-11-13
 * create by jieun
 */
val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}

