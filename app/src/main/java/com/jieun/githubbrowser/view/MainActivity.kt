package com.jieun.githubbrowser.view

import android.os.Bundle
import com.jieun.githubbrowser.BaseActivity
import com.jieun.githubbrowser.R
import com.jieun.githubbrowser.databinding.ActivityMainBinding
import com.jieun.githubbrowser.view.searchuser.SearchUserFragment

/**
 * date 2021-11-13
 * create by jieun
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(R.id.frame_lt, SearchUserFragment(), false)
    }
}