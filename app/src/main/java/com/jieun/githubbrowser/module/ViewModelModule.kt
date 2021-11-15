package com.jieun.githubbrowser.module



import com.jieun.githubbrowser.viewmodel.BaseViewModel
import com.jieun.githubbrowser.viewmodel.SearchUserViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/**
 * date 2021-11-13
 * create by jieun
 */
val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { SearchUserViewModel(get()) }

}